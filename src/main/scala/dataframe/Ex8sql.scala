package dataframe

import org.apache.spark.sql.SparkSession

object Ex8sql extends App {
  val spark = SparkSession.builder()
    .appName("DataFrames Basics")
    .config("spark.master", "local")
    .getOrCreate()

  val stateNames = spark.read.parquet("./stateNames")
  stateNames.show()
  stateNames.printSchema()



}
