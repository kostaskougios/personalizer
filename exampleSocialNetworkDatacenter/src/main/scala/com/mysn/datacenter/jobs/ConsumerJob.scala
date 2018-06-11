package com.mysn.datacenter.jobs

import com.akt.personalizer.consumers.kafka.KafkaConsumer
import com.aktit.personalizer.model.time.TimeSplitter
import com.mysn.personalizer.tables.{Post, View}
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * @author kostas.kougios
  *         10/06/18 - 19:51
  */
object ConsumerJob
{
	def main(args: Array[String]): Unit = {
		val conf = new SparkConf().setAppName(getClass.getName)
		val kafkaBootstrapServers = conf.get("spark.bootstrap.servers")
		val dataDir = conf.get("spark.data.dir")

		val timeSplitter = TimeSplitter.ByHour

		val ssc = new StreamingContext(conf, Seconds(2))
		try {
			KafkaConsumer.consume(ssc, Post, kafkaBootstrapServers, dataDir, timeSplitter)
			KafkaConsumer.consume(ssc, View, kafkaBootstrapServers, dataDir, timeSplitter)

			ssc.start()
			ssc.awaitTermination()
		} finally {
			ssc.stop()
		}
	}
}
