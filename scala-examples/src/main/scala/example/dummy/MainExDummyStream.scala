package example.dummy

import scala.util.Random
import scala.collection.mutable.ListBuffer

object MainExDummyStream extends App {

  //  val ex_list_mut = ListBuffer(4, 5, 22)
  //  ex_list_mut += 88
  //  println(ex_list_mut)
  //
  //  val ex_list = 42 :: Nil
  //  ex_list.head
  //  ex_list.tail

  // ---------------

  val items: Seq[Int] = for (i <- 0 to 100) yield Random.nextInt(1000)

  val numbers: Seq[Int] = items.toStream

  def view(values: Seq[Int]) {

    values match {

      case Nil => println("end!")

      case h :: tail =>
        println(h)
        view(tail)

    }

  }

  println(numbers)

  println(numbers.mkString("|"))

  //  var elements = Stream(Stream(1), Stream.Empty)
  //  println("elements:")
  //  println(elements.flatten.mkString("|"))

  val elements = 11 #:: (42 #:: Stream(1))

  println(elements.mkString(";"))

}