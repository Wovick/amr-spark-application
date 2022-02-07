package dataframe

import org.apache.spark.sql.functions.col
import org.apache.spark.sql.types._
import org.apache.spark.sql.{SaveMode, SparkSession}

object Ex1sql extends App {
  val par = 2

  val spark = SparkSession.builder()
    .appName("DataFrames Basics")
    .config("spark.master", s"local[$par]")
    .getOrCreate()

  val stateNames = spark.read
    .option("header", "true")
    .option("inferSchema", "true")
    .csv("C:\\Users\\Vadim\\IdeaProjects\\kama-spark-scala\\src\\main\\resources\\stateNames\\statenames.csv")

  stateNames.show()
  stateNames.printSchema()

  stateNames
    .coalesce(1)
    .write
    .mode(SaveMode.Overwrite)
    .parquet("./stateNames")


  // Step - 2: In reality it can be too expensive and CPU-burst
  // If dataset is quite big, manual schema definition is preferred


  val fields = Array(
    DataTypes.createStructField("State", StringType, true),
    DataTypes.createStructField("Gender", StringType, true),
    DataTypes.createStructField("Year", IntegerType, true),
    DataTypes.createStructField("Name", StringType, false),
    DataTypes.createStructField("Cnt", IntegerType, true))

  //val nationalNamesSchema = DataTypes.createStructType(fields)
  val nationalNamesSchema = StructType(fields)

  val nationalNames = spark.read
    .option("header", "true")
    .schema(nationalNamesSchema)
    .csv("src/main/resources/stateNames/statenames.csv")
    .repartition(col("Year"))


  val cachedDF = nationalNames.cache

//  nationalNames.show()
//  nationalNames.printSchema()
//
  val readyToWriteDF = cachedDF
    .coalesce(1)

  readyToWriteDF.write.mode(SaveMode.Overwrite).json("./nationalNames")
  readyToWriteDF.write.mode(SaveMode.Overwrite).orc("./nationalNames-orc")
  readyToWriteDF.write.mode(SaveMode.Overwrite).parquet("./nationalNames-pq")

//
//  // Step - 3: Simple dataframe operations
//  // Filter & select & orderBy
//  cachedDF
//    .select("Name", "Year", "Cnt")
//    .where("Gender == 'M'")
//    .orderBy("Name", "Year")
//    .explain()

  cachedDF
    .groupBy("Year")
    .sum("Cnt").as("Sum")
    .orderBy("Year")
    .explain()
}