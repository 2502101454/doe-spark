package org.apache.spark.doe

import org.apache.spark.{SecurityManager, SparkConf}
import org.apache.spark.rpc.{RpcCallContext, RpcEndpointRef, RpcEnv, ThreadSafeRpcEndpoint}

import java.util.concurrent.ScheduledThreadPoolExecutor
import scala.collection.mutable

/**
 *
 * @author zengwang
 * @create 2024-01-14 11:06
 * @desc:
 */
class Master(val rpcEnv: RpcEnv) extends ThreadSafeRpcEndpoint{
  // Endpoint 生命周期: constructor -> onStart -> receive* -> onStop
  var idToWorker = new mutable.HashMap[String, WorkerInfo]()

  // 启动定时器，定时扫描当前活跃的worker
  override def onStart(): Unit = {
    // 1个线程即可
    val executor = new ScheduledThreadPoolExecutor(1)
    // 下面两个时间单位都是毫秒，首次执行的时，先等6s再执行，随后周期每15秒扫描一次
    executor.scheduleAtFixedRate(new Runnable {
      override def run(): Unit = {
        val deadWorkers: Iterable[WorkerInfo] = idToWorker.values.filter(w => System.currentTimeMillis() - w.lastHeartbeatTime > 10000)
        deadWorkers.foreach(w => {
          idToWorker -= w.workerId
        })
        println(s"当前活跃的worker数量: ${idToWorker.size}")
      }
    }, 6000, 15000, java.util.concurrent.TimeUnit.MILLISECONDS)

  }

  override def receive: PartialFunction[Any, Unit] = {
    case "test" => println("收到test消息了")
    case RegisterWorker(rpcEndpointRef, workerId, workerMemory, workerCores) => {
      println(s"Master接送到了Worker发送过来的消息 workerId: $workerId, workerMemory: $workerMemory workerCores: $workerCores")
      // 保存worker的信息
      idToWorker.put(workerId, new WorkerInfo(workerId, workerMemory, workerCores))
      // Master向worker返回注册成功的信息
      // case class、case object 都实现了序列化接口
      rpcEndpointRef.send(RegisteredWorker)
      }
    // 对象类型匹配，拆解参数 是一种模式匹配写法
    case Heartbeat(workerId) => {
      println(s"Master收到Worker发送的心跳消息 workerId: $workerId")
      // 更新worker的最后心跳时间
      val workerInfo: WorkerInfo = idToWorker(workerId)
      workerInfo.lastHeartbeatTime = System.currentTimeMillis()
    }
  }

  // 接送同步消息的方法
  override def receiveAndReply(context: RpcCallContext): PartialFunction[Any, Unit] = {
    case "ask-msg" =>
      println("Master 收到了Worker发送的同步消息")
      // 向worker返回消息
      Thread.sleep(50000)
      context.reply("reply-msg")
  }
}

object Master {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
    val securityManager = new SecurityManager(conf)
    // 1.创建RPCEnv
    val rpcEnv = RpcEnv.create("SparkMaster", "localhost", 8888, conf, securityManager)
    // 2.创建RPCEndPoint
    val rpcEndpoint = new Master(rpcEnv)
    // 3.使用RPCEnv注册 RPCEndPoint
    val masterEndpointRef: RpcEndpointRef = rpcEnv.setupEndpoint("Master", rpcEndpoint)
    // 4.使用RPCEndPointRef发送消息
//    masterEndpointRef.send("test")

    // 5.将程序挂起，等待退出
    rpcEnv.awaitTermination()
  }
}
