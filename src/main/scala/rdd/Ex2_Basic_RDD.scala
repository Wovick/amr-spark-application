package rdd

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession
import org.apache.spark.storage.StorageLevel.MEMORY_AND_DISK

object Ex2_Basic_RDD extends App {
  val spark = SparkSession.builder()
    .appName("DataFrames Basics")
    .config("spark.master", "local[2]")
    .getOrCreate()

  val sc: SparkContext = spark.sparkContext

  val cachedInts = sc.textFile("./ints")
    .map(x => x.toInt)
    .persist(MEMORY_AND_DISK)

  val doubles = cachedInts.map(x => x * 2)
  println("== Doubles")
  doubles.collect().foreach(println)

  val even = cachedInts.filter(x => x % 2 == 0)
  println("== Even")
  even.collect().foreach(println)

  even.setName("Even numbers")
  println("Name is " + even.name + " id is " + even.id)
  println(even.toDebugString)

  cachedInts.unpersist()
  println("Multiply all numbers => " + even.reduce((a, b) => a * b))
  println(even.toDebugString)

  val groups: RDD[(Int, Int)] = cachedInts.map {
    case x if x % 2 == 0 => (0, x)
    case x => (1, x)
  }

  println("== Group");
  println(groups.groupByKey().toDebugString)

    val array = groups.groupByKey().coalesce(3).collect()
  array.foreach(println)
  val intToLong: collection.Map[Int, Long] = groups.countByKey()
  println(intToLong.toString())


  println("== Actions")
  println("First elem is " + cachedInts.first())
  println("Count is " + cachedInts.count())
  println("Take(2)")

  cachedInts.take(2).foreach(println)
  println("Take ordered (5)")
  cachedInts.takeOrdered(5).foreach(println)
}
