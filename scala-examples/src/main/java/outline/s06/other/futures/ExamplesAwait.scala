package outline.s06.other.futures

import scala.util.Random
import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.concurrent.Future

import scala.concurrent.ExecutionContext.Implicits._

object ExamplesAwait extends App {

  def futureSleep(value: Int = 120000): Future[String] = Future {
    val num = Random.nextInt(value)
    Thread.sleep(num)
    s"${num} on ${value.toString()}"
  }

  val sleep = futureSleep(3000)
  val sleep_res = Await.result(sleep, Duration.Inf)

  println(sleep.getClass.getSimpleName + "> " + sleep)
  println(sleep_res.getClass.getSimpleName + "> " + sleep_res)

}