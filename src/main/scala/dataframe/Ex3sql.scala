package dataframe

import org.apache.spark.sql.{DataFrame, SparkSession}

object Ex3sql extends App {
  val spark = SparkSession.builder()
    .appName("DataFrames Basics")
    .config("spark.master", "local")
    .getOrCreate()

  // ASSERT: Files should exists
  val stateNames = spark.read.parquet("C:\\Users\\Vadim\\IdeaProjects\\kama-spark-scala\\nationalNames-pq")
  stateNames.show()
  stateNames.explain()

  stateNames.createOrReplaceTempView("stateNames")

  // Step-1: Get full list of boy names
  val fromSQLDf: DataFrame = spark.sql("SELECT DISTINCT Name FROM stateNames WHERE Gender = 'M' ORDER BY Name")


  fromSQLDf.show(100)



  // Step-2: Get proportion of state NY births in total births
  val nationalNames = spark.read.json("C:\\Users\\Vadim\\IdeaProjects\\kama-spark-scala\\nationalNames")

  nationalNames.createOrReplaceTempView("nationalNames")

  val result = spark.sql(
    """
      |SELECT /*+  BROADCASTJOIN(small) */ * nyYear as year, stateBirths/usBirths as proportion, stateBirths, usBirths
      |FROM (SELECT year as nyYear, SUM(cnt) as stateBirths
      |      FROM stateNames WHERE state = 'NY' GROUP BY year ORDER BY year) as NY
      |      JOIN (SELECT year as usYear, SUM(cnt) as usBirths
      |            FROM nationalNames GROUP BY year ORDER BY year) as US ON nyYear = usYear
    """.stripMargin)


  result.show(150)
  result.explain(true)



}
