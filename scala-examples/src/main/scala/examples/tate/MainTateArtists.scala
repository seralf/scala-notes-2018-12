package examples.tate

import scala.io.Source
import scala.concurrent.Future

import scala.concurrent.ExecutionContext.Implicits.global._

object MainTateArtists01 extends App {

  val artists_url = "https://raw.githubusercontent.com/tategallery/collection/master/artist_data.csv"

  val src = Source.fromURL(artists_url)("UTF-8")

  val lines = src.getLines().toList

  lines
    .map { line => line.reverse }
    .foreach { item =>

      Thread.sleep(200)
      println(item)

    }

  val content = lines.mkString
  println(content.reverse)

  src.close()

  // SEE
  //  val content: Stream[String] = src.getLines().toStream
  //  content
  //    .zipWithIndex
  //    .foreach {
  //      case (line, i) =>
  //        println(i + ":" + line)
  //    }
  //  src.close()

}

object MainTateArtists02 extends App {

  val artists_url = "https://raw.githubusercontent.com/tategallery/collection/master/artist_data.csv"

  val src = Source.fromURL(artists_url)("UTF-8")

  using(src) { input =>
    input.getLines().toStream
  }(_.close())
    .foreach(println)

  // naive example
  def using[T <: { def close() }, R](resource: T)(action: T => R)(rsc: T => Unit): R = {

    action(resource)

  }

}