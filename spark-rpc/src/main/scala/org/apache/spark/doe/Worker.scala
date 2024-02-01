package org.apache.spark.doe

import org.apache.spark.{SecurityManager, SparkConf}
import org.apache.spark.rpc.{RpcAddress, RpcEndpointRef, RpcEnv, ThreadSafeRpcEndpoint}

import java.util.concurrent.{Executors, ScheduledExecutorService, TimeUnit}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
 *
 * @author zengwang
 * @create 2024-01-14 12:42
 * @desc:
 */
class Worker(val rpcEnv: RpcEnv) extends ThreadSafeRpcEndpoint{
  // constructor -> onStart -> receive* -> onStop
  var masterEndpointRef: RpcEndpointRef = _
  val WORKER_ID = "worker02"
  override def onStart(): Unit = {
    println("ssssssss")
    // worker向master建立连接
    masterEndpointRef = rpcEnv.setupEndpointRef(new RpcAddress("localhost", 8888), "Master")
    // Worker向master发送 我们封装的注册消息(附带自己的ref，Master那边调用)
    // self 是自己的Ref, this 是自己的对象
    masterEndpointRef.send(RegisterWorker(self, WORKER_ID, 1024, 8))
  }

  override def receive: PartialFunction[Any, Unit] = {
    case RegisteredWorker => {
      println("Worker收到Master的响应了")
      /*// 向Master发送一个同步消息
      val future: Future[String] = masterEndpointRef.ask[String]("ask-msg")
      // scala中使用map 中取future的结果
      future.map(res => println(res))*/

      // 启动定时器发送心跳
      val service: ScheduledExecutorService = Executors.newScheduledThreadPool(1)
      service.scheduleAtFixedRate(new Runnable {
        override def run(): Unit = {
          masterEndpointRef.send(Heartbeat(WORKER_ID))
        }
      }, 0, 1000, TimeUnit.MILLISECONDS)
    }

  }
}

object Worker {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
    val securityManager = new SecurityManager(conf)
    // 1.创建RPCEnv (Worker进程了，因此本地不能端口冲突)
    val rpcEnv = RpcEnv.create("SparkWorker", "localhost", 9999, conf, securityManager)
    // 2.创建RPCEndPoint
    val rpcEndpoint = new Worker(rpcEnv)
    // 3.使用RPCEnv注册 RPCEndPoint(只有这样，这个RPCEndPoint才可以正常收发消息)
    rpcEnv.setupEndpoint("Worker", rpcEndpoint)

    // 4.将程序挂起，等待退出
    rpcEnv.awaitTermination()
  }
}
