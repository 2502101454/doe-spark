package top.doe.spark.rpc

import top.doe.spark.netty.NettyRpcEnvFactory
import top.doe.spark.util.RpcAddress

/**
 *
 * @author zengwang
 * @create 2024-02-01 10:40
 * @desc:
 */
// 用来创建一个具体的RpcEnv(现在Spark使用NettyRpcEnv，先前版本使用AK Actor)，然后创建、注册RpcEndpoint
abstract class RpcEnv {

  // 注册RpcEndpoint
  def setupEndpoint(name: String, endpoint: RpcEndpoint): RpcEndpointRef

  // 获取本地的RpcEndpoint引用
  def endpointRef(endpoint: RpcEndpoint): RpcEndpointRef

  // 获取远端的RpcEndpointRef
  def setupEndpointRef(address: RpcAddress, endpointName: String): RpcEndpointRef

}

object RpcEnv {
  def create(name: String, host: String, port: Int): RpcEnv = {
    val conf: RpcEnvConfig = RpcEnvConfig(name, host, port, 1)
    new NettyRpcEnvFactory().create(conf)
  }
}

case class RpcEnvConfig(
                       name: String, // RpcEnv的名称
                       bindAddress: String, // RpcEnv绑定的地址
                       port: Int, // RpcEnv绑定的端口
                       numUsableCores: Int // 消息循环线程池中线程对象的数量
                       )
