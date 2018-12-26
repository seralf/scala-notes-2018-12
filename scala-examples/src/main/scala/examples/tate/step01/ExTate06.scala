package examples.tate.step01

/**
 * - added implicit encoding
 */
object ExTate06 extends App {

  import example06._

  val artists_url = "https://raw.githubusercontent.com/tategallery/collection/master/artist_data.csv"

  val csv = CSV.fromURL(artists_url)

  csv
    .parseLines()
    .zipWithIndex
    .foreach {
      case (line, i) =>
        println(i + ":" + line)
    }

}

package example06 {

  import scala.io.Source

  object CSV {

    def fromURL(url: String)(implicit encoding: String = "UTF-8") =
      new CSV(url, encoding)

  }

  class CSV(url: String, encoding: String) {

    val RGX_SPLIT = "," // NOTE: this has some issue!

    def parseLines(): Seq[Seq[String]] = this.parseAsList().map { line =>
      split(line)
    }

    private def parseAsList(): Seq[String] = {

      val src = Source.fromURL(url)(encoding)

      val content: Seq[String] = src.getLines().toList

      src.close()

      content
    }

    def split(txt: String) = txt.split(RGX_SPLIT).toList

  }

}
