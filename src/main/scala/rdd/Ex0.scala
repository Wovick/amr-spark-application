package rdd

import org.apache.spark.sql.{Dataset, SparkSession}

import java.lang

object Ex0 extends App {
  val spark = SparkSession.builder()
    .appName("DataFrames Basics")
    .config("spark.master", "local[2]")
    .getOrCreate()
  val context = spark.sparkContext

  println(s"RDD")
  val startTime: Long = System.currentTimeMillis
  val rdd = context.parallelize(1 to 100000000)
  val initRddFinishTime: Long = System.currentTimeMillis
  println(rdd.count())
  val rddCountTime: Long = System.currentTimeMillis
  calcTimeDiff(initRddFinishTime, startTime, rddCountTime)

  println(s"DataSet to RDD")
  val startDataFrameTime2: Long = System.currentTimeMillis
  val ds2: Dataset[lang.Long] = spark.range(100000000)
  val initDFTime2: Long = System.currentTimeMillis
  println(ds2.rdd.count())
  val countDFTime2: Long = System.currentTimeMillis
  calcTimeDiff(initDFTime2, startDataFrameTime2, countDFTime2)

  println(s"DataSet")
  val startDataFrameTime: Long = System.currentTimeMillis
  val ds: Dataset[lang.Long] = spark.range(100000000)
  val initDFTime: Long = System.currentTimeMillis
  println(ds.count())
  val countDFTime: Long = System.currentTimeMillis
  calcTimeDiff(initDFTime, startDataFrameTime, countDFTime)

  Thread.sleep(10000)




  private def calcTimeDiff(initRddFinish: Long, start: Long, rddCount: Long) = {
    println(s"Diff init is ${initRddFinish - start}")
    println(s"Diff count is ${rddCount - start}")
  }

}
