package com.akt.personalizer.consumers.kafka

import com.akt.personalizer.rdd.ChannelInput
import com.akt.personalizer.rdd.PersonalizerRDDImplicits._
import com.aktit.personalizer.model.TableDef
import com.aktit.personalizer.model.time.TimeSplitter
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.LongDeserializer
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.dstream.InputDStream
import org.apache.spark.streaming.kafka010._

/**
  * @author kostas.kougios
  *         10/06/18 - 19:56
  */
object KafkaConsumer
{
	def createDirectStream[TABLE](
		ssc: StreamingContext,
		tableDef: TableDef[TABLE],
		kafkaBootstrapServers: String,
		kafkaExtraParams: Map[String, Object] = Map.empty,
		locationStrategy: LocationStrategy = LocationStrategies.PreferConsistent
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
			locationStrategy,
			ConsumerStrategies.Subscribe[Long, Array[Byte]](topics, kafkaParams)
		)
	}

	def consume[TABLE](
		ssc: StreamingContext,
		tableDef: TableDef[TABLE],
		kafkaBootstrapServers: String,
		dataDir: String,
		timeSplitter: TimeSplitter,
		kafkaExtraParams: Map[String, Object] = Map.empty,
		locationStrategy: LocationStrategy = LocationStrategies.PreferConsistent
	) = {
		val messages = createDirectStream(ssc, tableDef, kafkaBootstrapServers, kafkaExtraParams, locationStrategy)
		messages.foreachRDD { rdd =>
			rdd.map(cr => ChannelInput(cr.key, cr.value)).saveAsDataCenterFile(dataDir, tableDef, timeSplitter)
			// The consumer offsets won't automatically be stored. We need to update
			// them here because we consumed some data.
			// See https://spark.apache.org/docs/2.3.0/streaming-kafka-0-10-integration.html#storing-offsets
			// regarding other options for storing the kafka offsets.
			val offsetRanges = rdd.asInstanceOf[HasOffsetRanges].offsetRanges
			messages.asInstanceOf[CanCommitOffsets].commitAsync(offsetRanges)
		}
	}
}
