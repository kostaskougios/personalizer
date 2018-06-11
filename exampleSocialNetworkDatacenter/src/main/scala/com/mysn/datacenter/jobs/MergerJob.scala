package com.mysn.datacenter.jobs

import com.akt.personalizer.files.HDFS
import org.apache.spark.SparkConf

/**
  * @author kostas.kougios
  *         11/06/18 - 00:20
  */
object MergerJob
{
	def main(args: Array[String]): Unit = {
		val conf = new SparkConf().setAppName(getClass.getName)
		val dataDir = conf.get("spark.data.dir")
		val hdfs = HDFS("hdfs://server.lan")
		//		println(
		//			hdfs.listStatus(Directories.incomingDataDirectory(dataDir, Post)).toList
		//		)

		//		val sc = new SparkContext(conf)
		//		try {
		//			val rdd = sc.dataCenterFile("hdfs://server.lan/social-network/incoming/social.Post/*")
		//			val x = rdd.map(_.deserialize(Post)).take(10)
		//			println(x.toList)
		//		} finally {
		//			sc.stop()
		//		}
	}
}
