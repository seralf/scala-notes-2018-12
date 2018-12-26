package examples.tate.step01

import scala.collection.immutable.ListMap
import scala.collection.immutable.Map
import scala.concurrent.Future
import scala.util.Random

object ExTateIdeaDataTable extends App {

  import ideaDataTable._

  val artists_url = "https://raw.githubusercontent.com/tategallery/collection/master/artist_data.csv"

  val csv = CSVParser.fromURL(artists_url).parse()

  //  csv
  //    .rows
  //    .zipWithIndex
  //    .foreach {
  //      case (line, i) =>
  //        println(i + ":" + line.size + ":" + line)
  //    }

  println(csv.view())

}

package ideaDataTable {

  import scala.io.Source
  import scala.collection.mutable.LinkedHashMap

  //  import scala.concurrent.ExecutionContext.Implicits._

  // TODO case class Header(name: String, datatype: Class[_] = classOf[String], size: Int = 0)

  case class DataTable(
    headers: Seq[String],
    rows:    Seq[Map[String, Any]]) {

    def sample(sampled: Int = 1) = {

      val how_many = rows.foldRight(0)((_, x) => x + 1)

      val max = if (how_many < sampled) how_many else sampled

      // TODO: Random.nextInt(...)

      val from = 0
      val until = from + max

      rows.slice(from, until)
    }

    def col_size_estimation(samples: Int) = this.sample(samples)
      .flatMap(x => x.map(p => (p._1, p._2.toString().size)))
      .groupBy(_._1).map(_._2.max)

    def view() = {

      val samples = 10

      val col_size: Map[String, Int] = col_size_estimation(samples)

      val trim = 60

      val headers_text = headers.map(s => String.format(s"%-${col_size.getOrElse(s, 0)}s", s))

      //      s"| ${headers.map(String.format(s"%-${trim}s", _)).mkString("\t | ")}\t |\n" +
      //        s"| ${headers.map(_ => List.fill(trim - 1)("-").mkString).mkString("\t | ")}\t |\n" +
      //        this.sample(samples).map { row =>
      //          s"| ${row.map(_._2.toString()).map(String.format(s"%-${trim}s", _)).mkString("\t | ")}\t |"
      //        }.mkString("\n")

      // TODO

      s"| ${headers_text.mkString("\t | ")}\t | \n"

    }

  }

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




