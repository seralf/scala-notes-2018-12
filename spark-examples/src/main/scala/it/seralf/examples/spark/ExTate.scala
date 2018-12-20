package it.seralf.examples.spark

import org.apache.spark.sql.SparkSession
import java.nio.file.Paths
import org.apache.spark.sql.SaveMode
import org.apache.spark.sql.hive.thriftserver.HiveThriftServer2

object ExTate extends App {

  // CHECK val hadoop = Paths.get("C:/hadoop").normalize().toAbsolutePath()
  val hadoop = Paths.get("./hadoop_home/").normalize().toAbsolutePath()
  System.setProperty("hadoop.home.dir", hadoop.toString())

  // Create a SparkSession. No need to create SparkContext
  // You automatically get it as part of the SparkSession
  //  val warehouseLocation = "file:${system:user.dir}/spark-warehouse"
  val spark = SparkSession
    .builder()
    .appName("SparkSessionTateCollectionExample")
    // CHECK: .config("spark.sql.warehouse.dir", warehouseLocation)
    .config("spark.master", "local")
    // CHECK .config("hive.exec.scratchdir", "C:/tmp/hive")
    .enableHiveSupport()
    .getOrCreate()

  // example configs
  spark.conf.set("spark.sql.shuffle.partitions", 6)
  spark.conf.set("spark.executor.memory", "1G")

  // CHECK
  //  val configs: Map[String, String] = spark.conf.getAll(_)
  //  println("\n\nCONFIGS: " + configs)

  // ................................................

  val catalog = spark.catalog
  println("\n\n\n\n....CATALOG")
  println(catalog.listDatabases())
  println("\n\n\n\n....CATALOG")

  // general informentions...
  spark.catalog.listDatabases.show(false)
  spark.catalog.listTables.show(false)

  val data_src = scala.io.Source.fromURL("https://raw.githubusercontent.com/tategallery/collection/master/artist_data.csv")

  val D = '"'
  val S = ','
  val rgx_split = s"""${S}(?=([^${D}]*"[^${D}]*${D})*[^${D}]*$$)"""

  import spark.implicits._

  // TODO: DATASET

  val artistsDF = spark.sparkContext
    .parallelize(data_src.getLines().toStream)
    .map(_.split(rgx_split))
    .zipWithIndex()
    .map { case (row, row_num) => Artist(row_num, row(0), row(1), row(1), row(2), row(3), row(4), row(5), row(6), row(7)) }
    .toDF().cache()

  artistsDF.show()

  artistsDF.persist().write.mode(SaveMode.Overwrite)
    .saveAsTable("tate_artists")

  // EXAMPLE SQL QUERY
  val results = spark.sql("""
    SELECT name 
    FROM `tate_artists` 
    WHERE (name LIKE '%John%')
  """)

  //  CSVInferSchema

  println("\n\nRESULTS")
  results.foreach(row => println(row))

  // Start the JDBC server
  //  val sql = new HiveContext(spark.sparkContext)
  val sql_ctx = spark.sqlContext.newSession()

  // CHECK
  HiveThriftServer2.startWithContext(sql_ctx)

  //  Thread.sleep(120000)
  //  spark.stop()

}

case class Artist(
  _row_num:     Long,
  id:           String,
  name:         String,
  gender:       String,
  dates:        String,
  yearOfBirth:  String,
  yearOfDeath:  String,
  placeOfBirth: String,
  placeOfDeath: String,
  url:          String)