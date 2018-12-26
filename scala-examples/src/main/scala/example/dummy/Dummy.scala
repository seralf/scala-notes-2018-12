package example.dummy

import java.util.Date

object Dummy extends App {

  val n = 42
  var x = n - 3 //  assert(square(2.0) != 4.0, "square doesn't work!")
  //  def square(x: Double): Double = x * x
  //  val test = if (1 < 2) "Yes" else println("No")

}

trait PersonDetails

trait Visitor {
  def id: String // Unique id assigned to each user
  def createdAt: Date // Date this user first visited the site

  // How long has this visitor been around?
  def age: Long = new Date().getTime - createdAt.getTime
}

case class User(
  id:        String,
  email:     String,
  createdAt: Date   = new Date()) extends PersonDetails with Visitor

object MainTimestamp extends App {

  new Timestamp(1).seconds
  Timestamp(1, 1, 1).seconds

  class Timestamp(val seconds: Long)

  object Timestamp {

    def apply(seconds: Int): Timestamp =
      new Timestamp(seconds)

    def apply(hours: Int, minutes: Int, seconds: Int): Timestamp =
      new Timestamp(hours * 60 * 60 + minutes * 60 + seconds)

  }

}

