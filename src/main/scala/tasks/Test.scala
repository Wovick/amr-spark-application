package tasks

import org.apache.spark.sql.{Dataset, SparkSession}

import scala.collection.immutable

object Test extends App {
  val spark = SparkSession.builder()
    .appName("DataFrames Basics")
    .config("spark.master", "local[8]")
    //    .config("spark.sql.codegen.comments", "true")
    .getOrCreate()

  import spark.implicits._


  val input = 1 to 1000000000

  val valueDS: Dataset[Int] = spark.sparkContext.parallelize(input, 1)
    .toDS()


  val startTimeDS: Long = System.currentTimeMillis
  valueDS
    .map{ x =>
      Thread.sleep(1000)
      val x1 = x + 1

      Thread.sleep(1000)
      x1 * 10
    }

    //    .map{ x =>
//      Thread.sleep(1000)
//      x + 1
//    }
//    .map{ x =>
//      Thread.sleep(1000)
//      x * 10
//    }
    .foreach(x => println(x))
//  val finishTimeDS: Long = System.currentTimeMillis
//  println(finishTimeDS - startTimeDS)




//  input
//    .map{ x =>
//    Thread.sleep(1000)
//    x + 1
//  }
//    .map{ x =>
//      Thread.sleep(1000)
//      println(x)
//      x * 10
//    }
//    .foreach(x => println(x))







}
