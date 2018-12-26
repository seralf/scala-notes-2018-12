package examples.tate.step01

import scala.collection.mutable.Map

object ExTate10 extends App {

  import example10._

  val artists_url = "https://raw.githubusercontent.com/tategallery/collection/master/artist_data.csv"

  val csv = CSVParser.fromURL(artists_url)

  csv
    .parse()
    .zipWithIndex
    .foreach {
      case (line, i) =>
        println(i + ":" + line.size + ":" + line)
    }

}

package example10 {

  import scala.io.Source
  import scala.collection.mutable.LinkedHashMap

  object CSVParser {

    def fromURL(url: String)(implicit delimiter: Char = '"', separator: Char = ',', encoding: String = "UTF-8") =
      new CSVParser(url, delimiter, separator, encoding)

  }

  class CSVParser(url: String, val D: Char, val S: Char, encoding: String) {

    val RGX_SPLIT = s"""${S}(?=([^${D}]*"[^${D}]*${D})*[^${D}]*$$)"""

    def parse(): Seq[Map[String, Any]] = {

      lazy val headers: List[String] = this.parseLines().head.toList

      this.parseLines().tail
        .map { line =>

          headers
            .zip(line)
            .foldLeft(Map[String, Any]())((map, pair) => map + pair)

        }

    }

    def parseLines(): Seq[Seq[String]] = {

      using(Source.fromURL(url)(encoding)) { input =>
        input.getLines().toStream
      }(_.close())
        .map(splitLine)

    }

    private def splitLine(txt: String) = txt.split(RGX_SPLIT).toList

    // naive example
    private def using[T <: { def close() }, R](resource: T)(action: T => R)(rsc: T => Unit): R = {

      action(resource)

    }

  }

}




