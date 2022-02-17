package rdd

import org.apache.hadoop.io.Text
import org.apache.spark.sql.SparkSession

object Ex4_Save_Load extends App {
  val spark = SparkSession.builder()
    .appName("DataFrames Basics")
    .config("spark.master", "local[2]")
    .getOrCreate()

  val sc = spark.sparkContext

  val profileData = List(("Ivan", "Java"), ("Elena", "Scala"), ("Petr", "Scala"))
  val programmerProfiles = sc.parallelize(profileData)

  programmerProfiles
    .map { row => (new Text(row._1), new Text(row._2)) }

    //    .saveAsNewAPIHadoopFile("./profiles", classOf[Text], classOf[Text], classOf[org.apache.hadoop.mapreduce.lib.output.TextOutputFormat[NullWritable, BytesWritable]])
//    .saveAsNewAPIHadoopFile("./profiles", classOf[Text], classOf[Text], classOf[org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat[NullWritable, BytesWritable]])

  // Read and parse data
  val castTypesFunction = (row: (Text, Text)) => (row._1.toString, row._2.toString)
  val profiles = sc.sequenceFile("./profiles", classOf[Text], classOf[Text]).map(castTypesFunction)

  profiles.collect().foreach(println)
}
