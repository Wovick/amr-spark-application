package rdd

import org.apache.spark.sql.SparkSession

object Ex6_Fold extends App {
  val spark = SparkSession.builder()
    .appName("DataFrames Basics")
    .config("spark.master", "local[2]")
    .getOrCreate()

  val sc = spark.sparkContext

  val data = List(("Elena", -15), ("Petr", 39), ("Elena", 290))
  val codeRows = sc.parallelize(data)

  val zeroCoder = ("zeroCoder", 0);

  val greatContributor = codeRows.fold(zeroCoder) {
    case (acc, coder) if acc._2 < Math.abs(coder._2) => coder
    case (acc, coder) => acc
  }

  println("Developer with maximum contribution is " + greatContributor)

  // Step-2: Group code rows by skill
  val codeRowsBySkillData = List(
    ("Java", ("Ivan", 240)),
    ("Java", ("Elena", -15)),
    ("PHP", ("Petr", 39)),
    ("PHP", ("Petr", 300))
  )

  val codeRowsBySkill = sc.parallelize(codeRowsBySkillData);

  val maxBySkill = codeRowsBySkill.foldByKey(zeroCoder) {
    case (acc, coder) if acc._2 > Math.abs(coder._2) => acc
    case (_, coder) => coder
  }

  val greatestContributor: Array[(String, (String, Int))] = maxBySkill.collect()

  println("Greatest contributor by skill are ")
  greatestContributor.foreach(println)


}
