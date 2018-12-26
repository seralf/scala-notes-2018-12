package examples.tate.step01

import scala.collection.mutable.Map

object ExTate09 extends App {

  import example09._

  //  CHECK: val artworks_url = "https://raw.githubusercontent.com/tategallery/collection/master/artwork_data.csv"
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

package example09 {

  import scala.io.Source
  import scala.collection.mutable.LinkedHashMap

  object CSV {

    def fromURL(url: String)(implicit delimiter: Char = '"', separator: Char = ',', encoding: String = "UTF-8") =
      new CSV(url, delimiter, separator, encoding)

  }

  class CSV(url: String, val D: Char, val S: Char, encoding: String) {

    val RGX_SPLIT = s"""${S}(?=([^${D}]*"[^${D}]*${D})*[^${D}]*$$)"""
    //    val RGX_SPLIT = "," // NOTE: this has some issue!

    def parse(): Seq[Map[String, Any]] = {

      lazy val headers: List[String] = this.parseLines().head.toList

      this.parseLines().tail
        .map { line =>

          headers
            .zip(line)
            .foldLeft(Map[String, Any]())((map, pair) => map + pair)

        }

    }

    // TODO: refactorization

    def parseLines(): Seq[Seq[String]] = this.parseAsStream
      .map { line =>
        splitLine(line)
      }

    private def parseAsStream(): Seq[String] = {

      using(Source.fromURL(url)(encoding)) { input =>
        input.getLines().toStream
      }(_.close())

    }

    // NOTE: we use this to avoit ERROR: Exception in thread "main" java.io.IOException: stream is closed

    @deprecated
    private def parseAsList(): Seq[String] = {

      val src = Source.fromURL(url)(encoding)

      val content: Seq[String] = src.getLines().toList

      src.close()

      content
    }

    private def splitLine(txt: String) = txt.split(RGX_SPLIT).toList

    // naive example
    private def using[T <: { def close() }, R](resource: T)(action: T => R)(rsc: T => Unit): R = {

      action(resource)

    }

  }

}




