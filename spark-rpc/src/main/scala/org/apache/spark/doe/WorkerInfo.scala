package org.apache.spark.doe

/**
 *
 * @author zengwang
 * @create 2024-01-15 10:00
 * @desc:
 */
class WorkerInfo(val workerId: String, var workerMemory: Int, var workerCores: Int) {
  var lastHeartbeatTime: Long = _
}
