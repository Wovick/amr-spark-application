package dataframe

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{callUDF, col, udf}

object Ex5sql extends App {
  val spark = SparkSession.builder()
    .appName("DataFrames Basics")
    .config("spark.master", "local")
    .getOrCreate()

  val stateNames = spark.read.parquet("C:\\Users\\Vadim\\IdeaProjects\\kama-spark-scala\\nationalNames-pq")
  stateNames.show()
  stateNames.printSchema()

  stateNames.cache

  // Step-1: Define and register UDF function
  val lambdaIsWorldWarTwoYear: Int => Boolean = (year: Int) => year >= 1939 && year <= 1945
  spark.sqlContext.udf.register("isWorldWarTwoYear", lambdaIsWorldWarTwoYear)

  // Step-2.1: Use UDF in dataframe (old way)
  println("Old way UDF result")
  stateNames
    .select(col("Year"), callUDF("isWorldWarTwoYear", col("Year")))
    .distinct
    .orderBy(col("Year"))
    .show(100)

  // Step-2.2: Use UDF in dataframe (using selectExpr)
  println("SelectExpr UDF result")
  stateNames.selectExpr("Year", "isWorldWarTwoYear(Year)")
    .distinct
    .orderBy(col("Year").desc)
    .show(150)

  // Step-2.3: Use UDF in dataframe (Scala way)
  println("Scala way UDF result")

  val lambdaIsWorldWarOneYear = udf((year: Int) => year >= 1914 && year <= 1918)

  stateNames
    .select(col("Year"), lambdaIsWorldWarOneYear.apply(col("Year")))
    .distinct.orderBy(col("Year"))
    .show(150)

  // Step-3: Use the same UDF in SQL expression
  stateNames.createOrReplaceTempView("stateNames")

//  hive managed
  stateNames.write.saveAsTable("stateNames")

  // Step-4: Get full list of boy names who was born during WWII
  spark.sql("SELECT DISTINCT Name FROM stateNames WHERE Gender = 'M' and isWorldWarTwoYear(Year) ORDER BY Name DESC").show(150)


}
