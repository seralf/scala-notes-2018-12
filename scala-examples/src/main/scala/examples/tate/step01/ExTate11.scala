package examples.tate.step01

import scala.collection.immutable.ListMap
import scala.collection.immutable.Map

object ExTate11 extends App {

  import example11._

  val artists_url = "https://raw.githubusercontent.com/tategallery/collection/master/artist_data.csv"

  val csv = CSVParser.fromURL(artists_url).parse()

  csv
    .rows
    .zipWithIndex
    .foreach {
      case (line, i) =>
        println(i + ":" + line.size + ":" + line)
    }

}

package example11 {

  import scala.io.Source
  import scala.collection.mutable.LinkedHashMap

  case class DataTable(
    headers: Seq[String],
    rows:    Seq[Map[String, Any]])

  // TODO: pretty print of CSV
  // TODO: sampling of CSV

  object CSVParser {

    def fromURL(url: String)(implicit delimiter: Char = '"', separator: Char = ',', encoding: String = "UTF-8") =
      new CSVParser(url, delimiter, separator, encoding)

  }

  class CSVParser(url: String, val D: Char, val S: Char, encoding: String) {

    val RGX_SPLIT = s"""${S}(?=([^${D}]*"[^${D}]*${D})*[^${D}]*$$)"""

    def parse(): DataTable = {

      lazy val headers: List[String] = this.parseLines().head.toList

      def parseRows = this.parseLines().tail
        .map { line =>

          headers
            .zip(line)
            .foldLeft(ListMap[String, Any]())((map, pair) => map + pair)

        }

      DataTable(headers, parseRows)

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




