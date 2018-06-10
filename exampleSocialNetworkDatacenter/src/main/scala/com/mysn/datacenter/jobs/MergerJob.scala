package com.mysn.datacenter.jobs

import org.apache.hadoop.io.{BytesWritable, LongWritable}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author kostas.kougios
  *         11/06/18 - 00:20
  */
object MergerJob
{
	def main(args: Array[String]): Unit = {
		val conf = new SparkConf().setAppName(getClass.getName)
		val dataDir = conf.get("spark.data.dir")
		val sc = new SparkContext(conf)
		try {
			val rdd = sc.sequenceFile("hdfs://server.lan/social-network/incoming/social.Post/1528672150190", classOf[LongWritable], classOf[BytesWritable])
			println(rdd.count())
		} finally {
			sc.stop()
		}
	}
}
