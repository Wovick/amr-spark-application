package rdd

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

object Ex1 extends App {
  val spark = SparkSession.builder()
    .appName("DataFrames Basics")
    .config("spark.master", "local[2]")
    .getOrCreate()

  val sc = spark.sparkContext

  val r = List(1, 2, 3, 4, 5, 6, 7)

  val ints: RDD[Int] = sc.parallelize(r).coalesce(1)

  ints.saveAsTextFile("./ints")
}
