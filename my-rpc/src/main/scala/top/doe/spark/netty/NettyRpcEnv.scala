package top.doe.spark.netty

import top.doe.spark.rpc.{RpcEndpoint, RpcEndpointRef, RpcEnv}
import top.doe.spark.util.RpcAddress

/**
 *
 * @author zengwang
 * @create 2024-02-01 16:25
 * @desc:
 */
class NettyRpcEnv extends RpcEnv{
  // 使用NettyRpcEnv将创建好的RpcEndpoint进行注册
  override def setupEndpoint(name: String, endpoint: RpcEndpoint): RpcEndpointRef = {
    // TODO
    null
  }

  override def endpointRef(endpoint: RpcEndpoint): RpcEndpointRef = ???

  override def setupEndpointRef(address: RpcAddress, endpointName: String): RpcEndpointRef = ???
}
