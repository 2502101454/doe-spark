package top.doe.spark.rpc

import top.doe.spark.util.RpcAddress

/**
 *
 * @author zengwang
 * @create 2024-02-01 10:32
 * @desc:
 */
// 向远端RpcEndpoint 发送消息，同时将自己的RpcEndpointRef发送过去，需要实现序列化接口
abstract class RpcEndpointRef extends Serializable {

  // 对应rpcEndpoint的名称
  def name: String

  // 对应rpcEndpoint的地址
  def address: RpcAddress

  // 发送异步消息
  def send(message: Any): Unit
}
