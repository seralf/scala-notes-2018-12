package outline.implicits

import outline.implicits.ImplicitsHelper._

object ExImplicits extends App {

  val x: Int = 42

  val msg = new IntAdapter(x).zeroTrail()
  println(msg)

  //  val msg = x.zeroTrail()
  //  println(msg)
}

object ImplicitsHelper {

  implicit class IntAdapter(value: Int) {

    def zeroTrail(): String = "00" + value // TODO

  }

}
