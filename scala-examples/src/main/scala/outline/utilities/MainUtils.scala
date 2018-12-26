package outline.utilities

object MainUtils extends App {

  val p = Person("mario", "rossi")
  val m = ModelAdapter.toMap(p)
  println(m)

  val map = Map("firstName" -> "Luigi", "lastName" -> "Rossi")
  val somebody = ModelAdapter.fromMap[Person](map)
  println(somebody)

}

case class Person(
  firstName: String,
  lastName:  String)