package top.doe.spark.netty

import top.doe.spark.rpc.{RpcEnv, RpcEnvConfig, RpcEnvFactory}

/**
 *
 * @author zengwang
 * @create 2024-02-01 16:29
 * @desc:
 */
class NettyRpcEnvFactory extends RpcEnvFactory{

  override def create(config: RpcEnvConfig): RpcEnv = {
    val nettyRpcEnv = new NettyRpcEnv()
    nettyRpcEnv
  }
}
