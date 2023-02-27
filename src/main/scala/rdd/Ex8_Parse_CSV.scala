package rdd

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

import scala.collection.immutable

object Ex8_Parse_CSV extends App {
  val spark = SparkSession.builder()
    .appName("DataFrames Basics")
    .config("spark.master", "local[2]")
    .getOrCreate()

  val sc = spark.sparkContext

  // read from file
  val stateNamesCSV: RDD[String] = sc.textFile("src/main/resources/statenames.csv")

  // split / clean data
  val headerAndRows: RDD[List[String]] = stateNamesCSV.map(line => line.split(",").toList.map(x => x.trim))

  // get header
  val header: immutable.Seq[String] = headerAndRows.first().toList

  val predicate: List[String] => Boolean = x => x != "Test1"
  // filter out header (eh. just check if the first val matches the first header name)
  val data = headerAndRows.filter(predicate)

  // splits to map (header/value pairs)
  val stateNames = data.map(splits => splits.zip(header).map { case (x, y) => y -> x }.toMap)

  // print top-5
//  stateNames.take(5).foreach(println)

//   stateNames.collect // Easy to get java.lang.OutOfMemoryError: GC overhead limit exceeded

//   you should worry about all data transformations to rdd with schema
    stateNames
      .filter(e => e.get("Name").get == "Anna")
      .take(5)
      .foreach(println)


}
