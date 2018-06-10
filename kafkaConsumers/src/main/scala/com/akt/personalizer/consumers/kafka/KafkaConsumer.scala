package com.akt.personalizer.consumers.kafka

import com.aktit.personalizer.model.TableDef
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.LongDeserializer
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.dstream.InputDStream
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}

/**
  * @author kostas.kougios
  *         10/06/18 - 19:56
  */
object KafkaConsumer
{
	def createConsumerRDD[TABLE](
		ssc: StreamingContext,
		tableDef: TableDef[TABLE],
		kafkaBootstrapServers: String,
		kafkaExtraParams: Map[String, Object] = Map.empty
	): InputDStream[ConsumerRecord[Long, Array[Byte]]] = {
		val topics = Set(tableDef.channelName)

		val kafkaParams = Map[String, Object](
			"bootstrap.servers" -> kafkaBootstrapServers,
			"key.deserializer" -> classOf[LongDeserializer],
			"value.deserializer" -> classOf[NoOpDeserializer],
			"group.id" -> (tableDef.channelName + "Group"),
			"auto.offset.reset" -> "latest",
			"enable.auto.commit" -> (false: java.lang.Boolean)
		) ++ kafkaExtraParams

		KafkaUtils.createDirectStream(
			ssc,
			LocationStrategies.PreferConsistent,
			ConsumerStrategies.Subscribe[Long, Array[Byte]](topics, kafkaParams)
		)
	}
}
