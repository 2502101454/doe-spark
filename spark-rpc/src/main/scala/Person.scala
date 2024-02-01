/**
 *
 * @author zengwang
 * @create 2024-01-14 10:15
 * @desc:
 */

class Person(name: String, age: Int)
// case class支持析构对象属性，普通类的对象不可以哦
case class Student(var score: Int) {}
class User() {}

object test {
  def matchTest: PartialFunction[Any, Unit] = {
    case Student(score) => println(s"match Student $score")
    case _ => println("nothing to match")
  }

  def main(args: Array[String]): Unit = {
    val student = new Student(90)
    matchTest(student) // match Student 90
    matchTest(111) // nothing to match

    // 偏函数的两种定义方式, def、val 都是给函数绑定一个名字而已
    val matchTest2: PartialFunction[Any, Unit] = {
      case Student(score) => println(s"match Student $score")
      case _ => println("nothing to match")
    }
    matchTest2(student) // match Student 90
    matchTest2(111) // nothing to match

    var f2: PartialFunction[Any, Int] = {
      case x: Int => x + 1
    }
    println(f2(1))
//    f2("aa")
    var a = List(1, 2, "aa", 3)
    // println(a.map(f2)) // MatchError When "aa"
    println(a.collect({ case x: Int => x + 1 }))

//    student match {
//      case Student(score) => println("match Student")
//      // case Person(name, age) => println("match Person") // 编译ERROR
//    }
  }
}