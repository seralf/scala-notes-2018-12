package example.dummy

object MainPersons extends App {

  val pers1 = Person("Mario", "Rossi")
  val pers2 = Person("Mario", "Rossi")

  println(pers1, pers2.reverse)
  println(pers1 == pers2)

  val text = pers1 match {
    case Person("Luke", "Skywalker") => "Stop, rebel scum!"
    case Person("Han", "Solo")       => "Stop, rebel scum!"
    case Person("mario", _)          => "ho trovato un Mario!"
    case obj: Object                 => "object! " + obj
    case unknown                     => s"Move along $unknown!"
  }

  println(text)

}

case class Person(firstName: String, lastName: String) {

  def reverse = firstName.reverse

}