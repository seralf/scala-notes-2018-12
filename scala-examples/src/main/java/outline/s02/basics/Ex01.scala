package outline.s02.basics

object Ex01 extends App {

  var x = "uno"

  println(x, x.getClass)

  x = x + 3

  println(x, x.getClass)
  assert(x.getClass == classOf[java.lang.String], "x is NOT java.lang.String!")

  var a = 33

  println(a, a.getClass())

  var b = a.toShort

  println(b, b.getClass())

}