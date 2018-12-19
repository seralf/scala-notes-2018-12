package it.seralf.examples.spark

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.sql.SQLContext
import java.nio.file.Paths

object ExScala02 extends App {

  val hadoop = Paths.get("./hadoop_home/").normalize().toAbsolutePath()
  System.setProperty("hadoop.home.dir", hadoop.toString())

  val conf = new SparkConf()
    .setAppName("example-02")
    .setMaster("local")

  val sc = new SparkContext(conf)

  val sqlContext = new SQLContext(sc)
  import sqlContext.implicits._

  val info = List(("mike", 24), ("joe", 34), ("jack", 55))
  val infoRDD = sc.parallelize(info)
  val people = infoRDD.map(r => Person(r._1, r._2)).toDF()
  people.createOrReplaceTempView("people")

  val subDF = sqlContext.sql("""
  SELECT * 
  FROM `people` 
  WHERE (age > 30)  
  """)

  subDF.show()

  sc.stop()
}

case class Person(name: String, age: Int)