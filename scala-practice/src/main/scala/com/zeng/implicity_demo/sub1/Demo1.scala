package com.zeng.implicity_demo.sub1

import com.zeng.implicity_demo.{comeFromPackage, age}

/**
 *
 * @author zengwang
 * @create 2024-01-27 11:20
 * @desc:
 */
object Demo1 {
  def sayNumber(implicit number: Int) = {
    println(s"your number is $number")
  }
  def main(args: Array[String]): Unit = {
    comeFromPackage()
   sayNumber
  }
}
