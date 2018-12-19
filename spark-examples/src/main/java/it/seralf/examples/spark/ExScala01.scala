package it.seralf.examples.spark

import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.slf4j.LoggerFactory
import java.nio.file.Paths

object ExScala01 extends App {

  val logger = LoggerFactory.getLogger(this.getClass)

  val hadoop = Paths.get("./hadoop_home/").normalize().toAbsolutePath()
  System.setProperty("hadoop.home.dir", hadoop.toString())

  val conf = new SparkConf()
    .setAppName("example-01")
    .setMaster("local")

  println(conf)

  val sc = new SparkContext(conf)

  // CHECK: Spark web UI at http://192.168.50.1:4040

  println(sc)

  val logData = sc.textFile("README.md")
  val count = logData.count()

  println("COUNT?" + count)

  println(s"spark> WEB UI: ${sc.uiWebUrl.get}")

  Thread.sleep(120000)

  //  sc.stop()

}