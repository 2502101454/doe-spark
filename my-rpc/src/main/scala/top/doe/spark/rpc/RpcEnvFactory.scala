package top.doe.spark.rpc

/**
 *
 * @author zengwang
 * @create 2024-02-01 16:27
 * @desc:
 */
trait RpcEnvFactory {
  // 传入RpcEnvConfig，创建RpcEnv
  def create(config: RpcEnvConfig): RpcEnv
}
