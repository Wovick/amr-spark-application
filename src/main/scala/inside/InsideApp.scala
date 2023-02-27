package inside

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.catalyst.InternalRow
import org.apache.spark.sql.functions.{col, expr}
import org.apache.spark.sql.{Dataset, Row, SparkSession}
import org.apache.spark.sql.types._
import org.apache.spark.sql.catalyst.expressions.codegen.UnsafeRowWriter


object InsideApp extends App {
  // creating a SparkSession
  val spark = SparkSession.builder()
    .appName("DataFrames Basics")
    .config("spark.master", "local")
//    .config("spark.sql.codegen.comments", "true")
    .getOrCreate()

  import spark.implicits._

  val valueDF =
    spark.sparkContext
      .parallelize(Seq(("k1", 123), ("k2", 234), ("k3", 345), ("k4", 567), ("k5", 567), ("k5", 567)))
      .toDF("first", "second")
      .filter($"second" === 234)

  val resultDF = valueDF
    .withColumn("new", expr("second + 1"))
    .sort($"second".desc, $"first")


  resultDF.explain(true)
  valueDF.show()


  private val rdd1: RDD[Row] = valueDF.rdd

  val rdd: RDD[Array[Byte]] = valueDF.queryExecution.toRdd.map(x => x.asInstanceOf[Array[Byte]])

  println(rdd.collect())

//  resultDF.explain(true)

  valueDF.queryExecution.debug.codegen

/*
 RDD[InternalRow] => RDD[Raw]
  +- Sort [second#9 DESC NULLS LAST, first#8 ASC NULLS FIRST], true, 0
  +- Exchange rangepartitioning(second#9 DESC NULLS LAST, first#8 ASC NULLS FIRST, 200), ENSURE_REQUIREMENTS, [id=#20]
  +- Project [_1#3 AS first#8, _2#4 AS second#9, (_2#4 + 1) AS new#12] => RDD[InternalRow]
  +- Filter (_2#4 = 234) => RDD[InternalRow]
  +- SerializeFromObject [staticinvoke(class org.apache.spark.unsafe.types.UTF8String, StringType, fromString, knownnotnull(assertnotnull(input[0, scala.Tuple2, true]))._1, true, false, true) AS _1#3, knownnotnull(assertnotnull(input[0, scala.Tuple2, true]))._2 AS _2#4]
  +- Scan[obj#2] ===> RDD[InternalRow]
*/

//  Thread.sleep(100000)
}
