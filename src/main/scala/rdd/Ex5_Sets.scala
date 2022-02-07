package rdd

import org.apache.spark.sql.SparkSession

object Ex5_Sets extends App {
  val spark = SparkSession.builder()
    .appName("DataFrames Basics")
    .config("spark.master", "local[2]")
    .getOrCreate()

  val sc = spark.sparkContext

  val jvmLanguages = sc.parallelize(List("Scala", "Java", "Groovy", "Kotlin", "Ceylon", "PHP"))
  val functionalLanguages = sc.parallelize(List("Scala", "Kotlin", "JavaScript", "Haskell"))
  val webLanguages = sc.parallelize(List("PHP", "Ruby", "Perl", "JavaScript", "PHP"))

  println()
  println("----Union----")
  val distinctLangs = webLanguages.union(jvmLanguages).distinct()
  println(distinctLangs.toDebugString)
  distinctLangs.collect().foreach(println)

  println()
  println("----Intersection----")
  val intersection = jvmLanguages.intersection(functionalLanguages);
  println(intersection.toDebugString)
  intersection.collect().foreach(println)

  println()
  println("----Substract----")
  val substraction = webLanguages.distinct().subtract(functionalLanguages);
  println(substraction.toDebugString)
  substraction.collect().foreach(println)

  println()
  println("----Cartesian----")
  val cartestian = webLanguages.distinct().cartesian(jvmLanguages);
  println(cartestian.toDebugString)
  cartestian.collect().foreach(println)

}
