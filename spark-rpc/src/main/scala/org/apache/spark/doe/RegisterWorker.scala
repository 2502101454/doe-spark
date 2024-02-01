package org.apache.spark.doe

import org.apache.spark.rpc.RpcEndpointRef

/**
 *
 * @author zengwang
 * @create 2024-01-14 12:54
 * @desc:
 */
case class RegisterWorker(rpcEndpointRef: RpcEndpointRef, workerId: String, workerMemory: Int, workerCores: Int)
