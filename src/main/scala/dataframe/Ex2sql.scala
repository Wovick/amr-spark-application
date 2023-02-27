package dataframe

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object Ex2sql extends App {
  val spark = SparkSession.builder()
    .appName("DataFrames Basics")
    .config("spark.master", "local")
    .getOrCreate()

  val stateNames = spark.read.parquet("C:\\Users\\Vadim\\IdeaProjects\\kama-spark-scala\\nationalNames-pq")
  stateNames.show();
  stateNames.printSchema();

  val filteredStateNames = stateNames
    .where("Year=1945 and Gender='F'")
    .select("Name", "State", "Cnt")

  filteredStateNames.cache()

  filteredStateNames.show()

  filteredStateNames
//    .select(array_contains(col("asdasd")))
//    .orderBy(col("State").desc(), col("Cnt").desc())
    .orderBy(col("State"), col("Cnt"))
    .show()


  val stateAndCount = filteredStateNames
    .groupBy("State")
    .agg(max("Cnt").as("max"));

  stateAndCount.show();



  // Self-join, of course
  val stateAndName = filteredStateNames
    .join(broadcast(stateAndCount),
      stateAndCount.col("max").equalTo(filteredStateNames.col("Cnt")).and(
        stateAndCount.col("state").equalTo(filteredStateNames.col("state"))),
      "full"
    )
    .select(filteredStateNames.col("state"), col("Name").alias("name")) // should choose only String names or $Columns
    .orderBy(col("state"), col("Cnt"));

  stateAndName.printSchema();
  stateAndName.show(100);
  stateAndName.explain();
  stateAndName.explain(true);

}
