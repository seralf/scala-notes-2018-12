package scala.homepage

import scala.concurrent.Future

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Await
import scala.concurrent.duration.Duration

object ExamplesFutures extends App {

  def get_results(x: Int): Future[Int] = Future {
    x + 1
  }

  val x = get_results(11 + 42)
  val y = get_results(22)

  //  val z = for (a <- x; b <- y) yield a * b

  val z = for {
    a <- x
    b <- y
  } yield a + b

  for (c <- z) println("Result is " + c)

  println("Meanwhile, the main thread goes on!")

  //  SEE
  //  z.map( _ + 1).onComplete(println)
  // Seq[Future[String]] -> Future[Seq[String]]
  // ... DO SOMETHING
  //  Await.result(z, Duration.Inf)

}

