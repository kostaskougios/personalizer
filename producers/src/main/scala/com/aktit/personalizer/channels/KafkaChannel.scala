package com.aktit.personalizer.channels

import java.util.Properties

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.apache.kafka.common.serialization.IntegerSerializer

/**
  * @author kostas.kougios
  *         07/06/18 - 23:39
  */
class KafkaChannel private(kafkaTopic: String, producer: KafkaProducer[Integer, Bytes]) extends Channel
{
	override def send(o: Array[Byte]): Unit = {
		val r = new ProducerRecord[Integer, Bytes](kafkaTopic, o)
		producer.send(r)
	}

	/**
	  * This has to be called when the channel is not needed anymore
	  */
	def destroy(): Unit = {
		producer.close()
	}

}

object KafkaChannel
{
	/**
	  * Use this to create the 1 and only jvm instance of KafkaProducer
	  *
	  * @param brokers a list of brokers in the form of "server.lan:9092"
	  * @return the KafkaProducer
	  */
	def producer(brokers: Seq[String]): KafkaProducer[Integer, Bytes] = {
		val props = new Properties()
		props.put("bootstrap.servers", brokers.mkString(","))
		props.put("client.id", getClass.getSimpleName)
		props.put("key.serializer", classOf[IntegerSerializer])
		props.put("value.serializer", classOf[RowSerializer])

		new KafkaProducer[Integer, Bytes](props)
	}

	def apply(kafkaTopic: String, producer: KafkaProducer[Integer, Bytes]): Channel = {
		new KafkaChannel(kafkaTopic, producer)
	}
}