package examples.tate.step01

object ExTate07 extends App {

  import example07._

  val artists_url = "https://raw.githubusercontent.com/tategallery/collection/master/artist_data.csv"

  val csv = CSV.fromURL(artists_url)

  csv
    .parseLines()
    .zipWithIndex
    .foreach {
      case (line, i) =>
        println(i + ":" + line.size + ":" + line)
    }

}

package example07 {

  import scala.io.Source

  object CSV {

    def fromURL(url: String)(implicit delimiter: Char = '"', separator: Char = ',', encoding: String = "UTF-8") =
      new CSV(url, delimiter, separator, encoding)

  }

  class CSV(url: String, val D: Char, val S: Char, encoding: String) {

    val RGX_SPLIT = s"""${S}(?=([^${D}]*"[^${D}]*${D})*[^${D}]*$$)"""
    //    val RGX_SPLIT = "," // NOTE: this has some issue!

    def parseLines(): Seq[Seq[String]] = this.parseAsList()
      .map { line =>
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
