package scala.homepage

import scala.concurrent.Future

import scala.concurrent.ExecutionContext.Implicits._

object ExamplesFutures extends App {

  val x = Future { 11 }
  val y = Future { 22 }

  val z = for (a <- x; b <- y) yield a * b

  for (c <- z) println("Result is " + c)

  println("Meanwhile, the main thread goes on!")

}

