package outline.s06.other.collections

import scala.collection.mutable.ListBuffer
import scala.util.Random

object ExCollections02 extends App {

  def generateNumbers(max: Int = 100, size: Int = 100000): Seq[Int] = {

    val buffer = ListBuffer[Int](size)

    for (n <- 0 to size - 1) {
      val num = Random.nextInt(max)
      buffer += num
    }

    buffer.toStream

  }

  //  SEE
  //  generateNumbers(100, 10).foreach{ n =>
  //    println(n)
  //  }

  val items = generateNumbers(100, 8)
  println("size: " + items.size, "items: " + items.mkString("|"))

  val ok = items
    .map(_ % 2)
    .filter(_ == 0)

  println("how many even? " + ok.size)

  // NOTE: there is no head reference here!
  println(generateNumbers(11, 4))

  println("\n\nmuch more...")
  val much_more = generateNumbers(8, 100000)
    .zipWithIndex
    // .par // CHECK the index!
    .map { case (x, i) => println(s"$i: $x"); x } // to see items
    .map { n =>
      (for (i <- 1 to n) yield "*").mkString
    }
  println("much_more: ")
  println(much_more.mkString("\n"))

}

object ExCollections01 extends App {

  //  Classes • Fields • Methods • Objects and instances • Inheritance, abstraction, encapsulation, polymorphism • Features

  val numbers = Array(3, 4, 9, 7, 1, 2, -4, 0, 12)
  val numbers2 = Array(42, 77, 9)

  println("sum " + numbers.sum)
  println("max number " + numbers.max)
  println("max as a string " + numbers.maxBy(x => x.toString()))
  println("min number " + numbers.min)

  // TODO: mean?

  val array_union = numbers.union(numbers2).mkString("|")
  println(array_union)
  println(array_union)

}


