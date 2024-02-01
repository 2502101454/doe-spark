package com.zeng

/**
 *
 * @author zengwang
 * @create 2024-01-27 11:02
 * @desc:
 */
package object implicity_demo {
  implicit var age = 20
  val name = "zengwang"

  def comeFromPackage() = {
    println(s"come from package, name: $name, age: $age")
  }
}
