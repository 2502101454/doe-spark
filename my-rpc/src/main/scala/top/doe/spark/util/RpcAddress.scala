package top.doe.spark.util

/**
 *
 * @author zengwang
 * @create 2024-02-01 10:36
 * @desc:
 */
case class RpcAddress(host: String, port: Int) {
  def hostPort: String = host + ":" +port

  override def toString: String = hostPort
}
