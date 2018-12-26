package examples.tate.step01

import scala.io.Source

object ExTate01 extends App {

  val artists_url = "https://raw.githubusercontent.com/tategallery/collection/master/artist_data.csv"

  val src = Source.fromURL(artists_url)("UTF-8")

  val content: Seq[String] = src.getLines().toList
  content
    .zipWithIndex
    .foreach {
      case (line, i) =>
        println(i + ":" + line)
    }
  src.close()

}

