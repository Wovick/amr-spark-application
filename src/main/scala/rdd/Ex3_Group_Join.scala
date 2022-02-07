package rdd

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

object Ex3_Group_Join extends App {
  val spark = SparkSession.builder()
    .appName("DataFrames Basics")
    .config("spark.master", "local[2]")
    .getOrCreate()

  val sc = spark.sparkContext
  val data = List(("Ivan", 240), ("Petr", 39), ("Elena", 290), ("Elena", 300))
  val codeRows: RDD[(String, Int)] = sc.parallelize(data)

  println("== Deduplicated")
  // Let's calculate sum of code lines by developer
  val reduced: RDD[(String, Int)] = codeRows.reduceByKey((x, y) => x + y)
  val deduplicated: RDD[(String, Int)] = codeRows.reduceByKey((x, y) => if(x > y) x else y)
  deduplicated.collect().foreach(println)

  println()
  println("== Folded")
  val folded: RDD[(String, Int)] = codeRows.foldByKey(1000) {
    case (x, y) => x + y
  }
  folded.collect().foreach(println)

  println()
  println("== Aggregated")
  val aggregated: RDD[(String, Int)] = codeRows.aggregateByKey(500)(_ + _, _ + _)
  aggregated.collect().foreach(println)


  // Or group items to do something else
  println()
  println("== Grouped")
  val grouped: RDD[(String, Iterable[Int])] = codeRows.groupByKey()
  grouped.collect().foreach(println)

  // Don't forget about joins with preferred languages
  val profileData = List(("Ivan", "Java"), ("Elena", "Scala"), ("Petr", "Scala"))
  val programmerProfiles = sc.parallelize(profileData)

  println()
  println("== Joined")
  val joined: RDD[(String, (String, Int))] = programmerProfiles.join(codeRows)
  println(joined.toDebugString)
  joined.collect().foreach(println)

  // also we can use special operator to group values from both rdd by key
  // also we sort in DESC order
  // co-group is performing grouping in the same executor due to which its performance is always better.
  println()
  println("== Cogroup")
  programmerProfiles.cogroup(codeRows).sortByKey(false).collect().foreach(println)

  // If required we can get amount of values by each key
  println()
  println("== CountByKey")
  println(joined.countByKey().toString())
//
  // or get all values by specific key
  println()
  println("== Lookup")
  println(joined.lookup("Elena").toString())

  // codeRows keys only
  println()
  println("== Keys")
  codeRows.keys.collect().foreach(println)

  // Print values only
  println()
  println("== Value")
  codeRows.values.collect().foreach(println)


}
