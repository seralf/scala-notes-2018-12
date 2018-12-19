package outline.s04.fp

import scala.util.Random
import scala.annotation.tailrec
import java.util.Date

object ExSimpleFunctions extends App {

  var res = sumTxt("11", "22")
  println(res)

  //  exercise>
  //  res = sumTxt("11", "due") // TODO: FIX!
  //  println(res)

  def sumTxt(a: String, b: String): String = {

    val num = a.toInt + b.toInt

    num.toString()

  }

}

object ExFunctions extends App {

  /* ---- call by name ---- */

  println("\n#### call by name")
  println("scelgo un numero! " + num)

  def num() = Random.nextInt(100)

  /* ---- named arguments ---- */

  println("\n#### named aguments")
  dummyFunction(second = 5, first = 7)

  def dummyFunction(first: Int, second: Int) {
    println("first argument is: " + first + ", then second is " + second)
  }

  /* ---- variable arguments ---- */

  println("\n#### variable arguments")
  fun01(1)
  fun01(4, 8, 17)
  fun01(Array(3, 9, 62): _*)

  def fun01(elements: Int*) {
    for (e <- elements) {
      println("element : " + e)
    }
  }

  /* ---- recursion ---- */

  println("\n#### recursion - fattoriale semplice")

  for (i <- 3 to 7)
    println(s"fattoriale di $i è ${factorial(i)}")

  println("\nfunzione fattoriale con @tailrec...")
  for (i <- 3 to 7)
    println(s"fattoriale di $i è ${factorialTail(i)}")

  def factorial(n: BigInt): BigInt = {
    if (n <= 1) 1
    else
      n * factorial(n - 1)
  }

  def factorialTail(i: Int): Long = {
    @tailrec
    def fact(i: Int, temp: Int): Long = {
      if (i <= 1) temp
      else fact(i - 1, i * temp)
    }
    fact(i, 1)
  }

  /* ---- default values ---- */

  println("\n#### default values")

  generateInterval()
  generateInterval(3, 11, 2)

  def generateInterval(start: Int = 0, end: Int = 4, step: Int = 1) {

    println(s"FOR [$start to $end by $step]")

    for (n <- start to end by step)
      print(n + " ")

    println

  }

  /* ----	higher-order functions ---- */

  println("\n#### higher order functions")

  val items = Array((2, 3), (8, 1), (4, 5))

  val results = functionOnSequence(items, (x, y) => x * y)
  results foreach println

  // ugly to read! :-)
  functionOnSequence(items, (x, y) => x + y) foreach println

  def functionOnSequence(elements: Seq[(Int, Int)], fun: (Int, Int) => Int): Seq[Int] =
    for (el <- elements)
      yield fun(el._1, el._2)

  /* ----	nested ---- */

  println("\n#### nested")

  // SEE: factorial!!

  val sorted = some_function(Array(55, 7, 13, 11, 22, 8))
  println(sorted)

  def some_function(items: Seq[Int]): Seq[Int] = {

    def nested_function(item: Int) = item * 2

    for (k <- items) yield nested_function(k)

  }

  /* ----	anonymous ---- */

  println("\n#### anonymous")

  var leading = (number: Int, leading: Int) => s"%0${leading}d".format(number)
  println("anonymous> leading " + leading(33, 7))

  // idea: get a list of properties...
  //  import scala.collection.JavaConversions._
  //  for (k <- System.getProperties.keys())
  //    println(k)

  var javaVersion = () => { System.getProperty("java.version") }

  println("anonymous> javaVersion " + javaVersion)
  println("anonymous> javaVersion " + javaVersion())

  //Partially Applied Functions	Currying Functions

  /* ----	partially applied ---- */

  println("\n#### partially applied")

  val date = new Date

  log(date, "first message")
  Thread.sleep(1000)

  logSameDate("second message")
  Thread.sleep(1000)

  logSameDate("third message")

  def log(date: Date, message: String) = {
    println(date + " : " + message)
  }
  def logSameDate = log(date, _: String)

  /* ----	curried ---- */

  println("\n#### curried")

  def test_add(x: Int)(y: Int) = x + y

  println(test_add(3)(4))
  println(test_add(3) _)

  logCurried01(date)("fourth message")
  logCurried02("fifth message")(date)

  def logCurried01(date: Date)(message: String) = {
    println(date + " : " + message)
  }

  def logCurried02(message: String)(date: Date) = {
    println(date + " : " + message)
  }

  // NOTE:  Currying: decomposition of functions with multiple arguments into a chain of single-argument functions.
  // NOTE:  Partial application of function: pass to a function fewer arguments than it has in its declaration.

  val fun_partial = logCurried02("sixth message")(_)
  Thread.sleep(1000)
  fun_partial(new Date)

}

object ExClosure extends App {

  val username = "Topolino"
  val show_msg = (msg: String) => s"${msg} ${username}!"

  println(show_msg("hello"))

}

object ExFunctionsRef extends App {

  val test1 = (txt: String) => txt.size
  val test2 = (txt: String, num: Int) => txt.size * num

  println(test1, test2)

}

object ExMoreFunctions extends App {

  def sum = (x: Int, y: Int) => x + y

  def view(x: Int, y: Int, fun: (Int, Int) => String) = fun(x, y)

  for {
    i <- 1 to 4
    j <- 7 to 17 by 3
  } {
    val txt = view(i, j, (x, y) => s"SUM of $x and $y is ${sum(x, y)}")
    println(txt)
  }

}

