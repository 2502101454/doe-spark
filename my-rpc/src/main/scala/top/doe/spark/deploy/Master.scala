package top.doe.spark.deploy

import top.doe.spark.rpc.{RpcEndpoint, RpcEndpointRef, RpcEnv}

/**
 *
 * @author zengwang
 * @create 2024-02-01 16:34
 * @desc:
 */
class Master(val rpcEnv: RpcEnv) extends RpcEndpoint{
  override def receive: PartialFunction[Any, Unit] = {
    case "test" => println("66666")
  }

}

object Master {
  def main(args: Array[String]): Unit = {
    val host = "localhost"
    val port = 8888
    // 1.创建RpcEnv
    val rpcEnv: RpcEnv = RpcEnv.create("SparkMaster", host, port)

    // 2.创建RpcEndpoint(master)
    val master: Master = new Master(rpcEnv)

    // 3.注册RpcEndpoint
    val masterRef: RpcEndpointRef = rpcEnv.setupEndpoint("Master", master)

    // 4.自己给自己发消息
    masterRef.send("test")

  }
}