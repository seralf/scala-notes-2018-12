package examples.tate.step01

import scala.collection.mutable.Map

object ExTate08 extends App {

  import example08._

  val artists_url = "https://raw.githubusercontent.com/tategallery/collection/master/artist_data.csv"

  val csv = CSV.fromURL(artists_url)

  csv
    .parse()
    .zipWithIndex
    .foreach {
      case (line, i) =>
        println(i + ":" + line.size + ":" + line)
    }

}

package example08 {

  import scala.io.Source
  import scala.collection.mutable.LinkedHashMap

  object CSV {

    def fromURL(url: String)(implicit delimiter: Char = '"', separator: Char = ',', encoding: String = "UTF-8") =
      new CSV(url, delimiter, separator, encoding)

  }

  class CSV(url: String, val D: Char, val S: Char, encoding: String) {

    val RGX_SPLIT = ","

    def parse(): Seq[Map[String, Any]] = {

      lazy val headers: List[String] = this.parseLines().head.toList

      this.parseLines().tail
        .map { line =>

          headers
            .zip(line)
            .foldLeft(Map[String, Any]())((map, pair) => map + pair)

        }

    }

    def parseLines(): Seq[Seq[String]] = this.parseAsList()
      .map { line =>
        split(line)
      }.toStream

    private def parseAsStream(): Seq[String] = ???

    private def parseAsList(): Seq[String] = {

      val src = Source.fromURL(url)(encoding)

      val content: Seq[String] = src.getLines().toList

      src.close()

      content
    }

    def split(txt: String) = txt.split(RGX_SPLIT).toList

  }

}




