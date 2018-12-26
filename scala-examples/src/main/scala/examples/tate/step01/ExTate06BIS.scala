//package examples.tate.step01
//
//import scala.io.Source
//
//object ExTate06 extends App {
//
//  val header = CSVLineParser.split(TateCSVReader.fromURL().head)
//
//  TateCSVReader.fromURL()
//    .tail
//    .map { line =>
//
//      header.zip(CSVLineParser.split(line))
//
//    }
//    .zipWithIndex.map(_.swap)
//    .foreach(println)
//
//}
//
//object CSVLineParser {
//
//  val D = '"'
//  val S = ','
//  val RGX_SPLIT = s"""${S}(?=([^${D}]*"[^${D}]*${D})*[^${D}]*$$)"""
//  //  val RGX_SPLIT = "," // NOTE: this has some issue!
//
//  def split(txt: String) = txt.split(RGX_SPLIT).toList
//
//}
