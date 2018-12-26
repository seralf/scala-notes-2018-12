package examples.tate.step01

/**
 * - added implicit encoding
 */
object ExTate04 extends App {

  import example04._

  val artists_url = "https://raw.githubusercontent.com/tategallery/collection/master/artist_data.csv"

  val csv = CSV.fromURL(artists_url)

  csv
    .parseAsList()
    .zipWithIndex
    .foreach {
      case (line, i) =>
        println(i + ":" + line)
    }

}

package example04 {

  import scala.io.Source

  object CSV {

    def fromURL(url: String)(implicit encoding: String = "UTF-8") =
      new CSV(url, encoding)

  }

  class CSV(url: String, encoding: String) {

    def parseLines(): Seq[Seq[String]] = ???

    def parseAsList(): Seq[String] = {

      val src = Source.fromURL(url)(encoding)

      val content: Seq[String] = src.getLines().toList

      src.close()

      content
    }

  }

}
