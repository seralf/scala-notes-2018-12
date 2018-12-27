package examples.tate.step01

import scala.collection.immutable.ListMap
import scala.collection.immutable.Map
import java.io.File

object ExTate11 extends App {

  import example11._

  val artists_url = "https://raw.githubusercontent.com/tategallery/collection/master/artist_data.csv"

  println("\n\nmap as Seq[Map[String, _]]")
  CSVParser.fromURL(artists_url).parse[Map[String, _]]()
    .zipWithIndex
    .foreach {
      case (line, i) =>
        println(i + ":" + line)
    }

  println("\n\nmap as Seq[Artist]")
  CSVParser.fromURL(artists_url).parse[Artist]()
    .zipWithIndex
    .foreach {
      case (line, i) =>
        println(i + ":" + line)
    }

}

package example11 {

  import scala.io.Source

  case class Artist(
    `﻿id`:          String,
    `name`:         String,
    `placeOfBirth`: Option[String])

  object CSVParser {

    def fromURL(url: String)(implicit delimiter: Char = '"', separator: Char = ',', encoding: String = "UTF-8") =
      new CSVParser(url, delimiter, separator, encoding)

  }

  class CSVParser(url: String, val D: Char, val S: Char, encoding: String) {

    import scala.reflect._
    import scala.reflect.runtime.universe._

    val RGX_SPLIT = s"""${S}(?=([^${D}]*"[^${D}]*${D})*[^${D}]*$$)"""

    def parse[T: TypeTag: ClassTag](): Seq[T] = {

      lazy val headers: Seq[String] = this.parseLines().head

      this.parseLines().tail
        .map { line =>
          val row: Map[String, Any] = ListMap(headers.zip(line): _*)
          example11.ModelAdapter.fromMap(row)
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

  /**
   * This object collects some useful methods for case class to map conversion (back and forth).
   *
   * SEE: https://stackoverflow.com/a/24100624/1202374
   * SEE: https://stackoverflow.com/a/1227643/1202374
   * SEE: https://stackoverflow.com/a/25894047/1202374
   *
   * ASK: how to handle Java Bean?
   * TODO: handle fields with @BeanProperty! (for Java Bean compatibility)
   *
   * TODO: move it inside the common library kbaselib
   *
   * TODO: implicit wrapper
   *
   */
  object ModelAdapter {

    import java.net.URI
    import scala.reflect._
    import scala.reflect.runtime.universe._
    import scala.collection.JavaConversions._
    import scala.collection.JavaConverters._
    import scala.beans.BeanProperty

    /**
     * case class to map: creates a new Map, based on the content a given case class
     * (each case class field will be used to create a corresponding key in the Map)
     */
    def toMap(cc: Product): Map[String, Any] = {
      val values = cc.productIterator
      cc.getClass.getDeclaredFields.map {
        _.getName -> (values.next() match {
          case p: Product if p.productArity > 0 => toMap(p)
          case x                                => x
        })
      }.toMap
    }

    /**
     * map to case class: creates a new instance of a given case class,
     * using values from the input Map.
     * NOTE:
     * 	- each key in the map is mapped to a case class field,
     * 	- if there is no field which corresponds to a map key, it will be ignored (not mapped/used)
     */
    def fromMap[T: TypeTag: ClassTag](m: Map[String, _]): T = {

      // identity for Map[String, _]!
      if (classTag[T].toString().equals(classOf[Map[String, _]].getName.toString())) {

        m.asInstanceOf[T]

      } else {

        val rm = runtimeMirror(classTag[T].runtimeClass.getClassLoader)
        val classTest = typeOf[T].typeSymbol.asClass
        val classMirror = rm.reflectClass(classTest)

        val constructor = typeOf[T].decl(termNames.CONSTRUCTOR).asMethod
        val constructorMirror = classMirror.reflectConstructor(constructor)

        val constructorArgs: List[Any] = constructor.paramLists.flatten.map((param: Symbol) => {
          val paramName = param.name.toString
          // if a parameter is optional, it can be omitted!
          if (param.typeSignature <:< typeOf[Option[Any]]) m.get(paramName)
          // else take the required parameter, or throw an exception!
          else m.get(paramName)
            .getOrElse(throw new IllegalArgumentException(s"Map is missing required parameter ${paramName}!"))
        })

        constructorMirror(constructorArgs: _*).asInstanceOf[T]

      }

    }

  }

}




