package outline.utilities

import org.slf4j.Logger
import scala.concurrent.duration.Duration
import scala.concurrent.Future
import scala.concurrent.Await
import scala.util.Try
import scala.util.Success
import scala.util.Failure

object Utils extends App {

  def using[A, B <: { def close(): Unit }](closeable: B)(action: B => A): A = {
    try {
      action(closeable)
    } finally {
      closeable.close()
    }
  }

  implicit class AwaitFuture[R](future: Future[R]) {

    def await(implicit duration: Duration = Duration.Inf) = {
      Await.result(future, duration)
    }

  }

  // IDEA
  def logErrors[A](logger: Logger)(action: Logger => A)(implicit msg: String = ""): Try[A] = {

    Try {
      action(logger)
    } match {
      case Success(s) =>
        Success(s)
      case Failure(ex) =>
        logger.debug(s"${msg}\n${ex}")
        Failure(ex)
    }

  }
}