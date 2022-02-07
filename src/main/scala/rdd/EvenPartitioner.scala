package rdd

import org.apache.spark.Partitioner;

class EvenPartitioner(val numPartitions: Int) extends Partitioner {
  override def getPartition(key: Any): Int = if (key.toString.toInt % 2 == 0) 0 else 1
}
