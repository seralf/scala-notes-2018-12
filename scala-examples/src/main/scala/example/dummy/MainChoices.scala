package example.dummy

import scala.util.Try
import scala.concurrent.Future
import scala.util.Success
import scala.util.Failure

object MainChoices extends App {

  val oneOf: Either[String, Int] = Right(42)
  // NOT monad!

  val opt: Option[String] = Some("uno")
  // Some | None

  var tried: Try[String] = Try { "OK" }

  import scala.concurrent.ExecutionContext.Implicits._
  val fut: Future[String] = Future { "ok" }

  val fut2 = Future.fromTry(tried)

}

object MainTestTry extends App {

  val test = Try {

    42 / 0

  }

  test match {

    case Failure(err) =>
      err.printStackTrace()
      println("result is " + err)

    case Success(ok) => println("result is " + ok)

  }

}