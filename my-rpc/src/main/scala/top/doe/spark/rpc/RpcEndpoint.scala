package top.doe.spark.rpc

/**
 *
 * @author zengwang
 * @create 2024-01-19 12:46
 * @desc:
 */
// 用于提供Rpc服务，接收消息
trait RpcEndpoint {
  // TODO 持有RpcEnv的引用
  val rpcEnv: RpcEnv

  // 生命周期方法，在构造方法之后调用一次
  def onStart(): Unit = {
    // By default, do nothing
  }

  // 获取自己的引用
  final def self: RpcEndpointRef = {
    // TODO 以后再实现
    null
  }

  // 接收异步消息
  def receive: PartialFunction[Any, Unit] = {
    case _ => throw new Exception(self + "does not implement 'receive'")
  }
}
