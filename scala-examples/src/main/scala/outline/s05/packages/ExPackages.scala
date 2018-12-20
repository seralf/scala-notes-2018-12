package outline.s05.packages

object ExPackages extends App {

  import outline.s05.packages.maths.Calc
  val x = Calc.sum(4, 7, 11)
  println("result is " + x)

}

package maths {

  object Calc {

    def sum(numbers: Int*): Int = {

      var result = 0
      for (n <- numbers) result += n
      result

    }

  }

}