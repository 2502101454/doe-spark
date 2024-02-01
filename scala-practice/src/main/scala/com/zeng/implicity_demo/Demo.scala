package com.zeng.implicity_demo

/**
 *
 * @author zengwang
 * @create 2024-01-27 10:42
 * @desc:
 */
object Demo {
//  implicit var age = 20

  def sayAge(implicit age: Int) = {
      println(s"your age is $age")
  }
  def main(args: Array[String]): Unit = {
    // 以下隐式转换规则、常量、方法都定义在包对象中，该包下的文件中直接使用，不需要显式导入
    sayAge
    println(name)
    comeFromPackage()
  }
}


