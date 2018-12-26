package examples.tate.step01

import scala.io.Source
import scala.concurrent.Future

import scala.concurrent.ExecutionContext.Implicits.global._

object ExTate02 extends App {

  import example02._

  val artists_url = "https://raw.githubusercontent.com/tategallery/collection/master/artist_data.csv"

  val csv = new CSV(artists_url, "UTF-8")

  csv.parseAsList()
    .zipWithIndex
    .foreach {
      case (line, i) =>
        println(i + ":" + line)
    }

}

package example02 {

  import scala.io.Source

  class CSV(url: String, encoding: String) {

    def parseAsList() = {

      val src = Source.fromURL(url)(encoding)

      val content: Seq[String] = src.getLines().toList

      src.close()

      content
    }

  }

}


