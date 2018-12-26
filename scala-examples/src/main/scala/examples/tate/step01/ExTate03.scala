package examples.tate.step01

object ExTate03 extends App {

  import example03._

  val artists_url = "https://raw.githubusercontent.com/tategallery/collection/master/artist_data.csv"

  val csv = CSV.fromURL(artists_url, "UTF-8")

  csv.parseAsList()
    .zipWithIndex
    .foreach {
      case (line, i) =>
        println(i + ":" + line)
    }

}

package example03 {

  import scala.io.Source

  object CSV {

    def fromURL(url: String, encoding: String) = new CSV(url, encoding)

  }

  class CSV(url: String, encoding: String) {

    def parseAsList() = {

      val src = Source.fromURL(url)(encoding)

      val content: Seq[String] = src.getLines().toList

      src.close()

      content
    }

  }

}
