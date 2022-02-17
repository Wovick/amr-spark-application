package dataframe

import org.apache.spark.sql.{Dataset, Encoders, SparkSession}

object Ex4sql extends App {
  val spark = SparkSession.builder()
    .appName("DataFrames Basics")
    .config("spark.master", "local")
    .getOrCreate()

  import spark.implicits._

  val personEncoder = Encoders.product[StateNamesBean]
  val nameYearEncoder = Encoders.product[NameYearBean]

  val stateNames: Dataset[StateNamesBean] = spark
    .read
    .parquet("C:\\Users\\Vadim\\IdeaProjects\\kama-spark-scala\\nationalNames-pq")
    .as[StateNamesBean]

  stateNames.show()
  stateNames.printSchema()

  val filterFunction = (value: StateNamesBean) => value.gender == "F"
  val mapFunction = (value: StateNamesBean) => NameYearBean(value.name, value.cnt)

  val result = stateNames
    .where("Gender == 'M'")
    .filter(_.gender == "F")
    .map(mapFunction)(nameYearEncoder)
    .groupBy("name")
    .sum("cnt")

  result.show()
  result.explain(true)


  case class NameYearBean(name: String, cnt: Int)

  case class StateNamesBean(
                             name: String,
                             year: Long = 0L,
                             gender: String,
                             state: String,
                             cnt: Int
                           )


}
