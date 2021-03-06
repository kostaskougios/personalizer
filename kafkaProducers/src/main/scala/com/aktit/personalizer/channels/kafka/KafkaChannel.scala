package com.aktit.personalizer.channels.kafka

import java.util.Properties

import com.aktit.personalizer.channels.{Bytes, Channel}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.apache.kafka.common.serialization.LongSerializer

/**
  * @author kostas.kougios
  *         07/06/18 - 23:39
  */
class KafkaChannel private(kafkaTopic: String, producer: KafkaProducer[Long, Bytes]) extends Channel
{
	override def send(time: Long, data: Array[Byte]): Unit = {
		val r = new ProducerRecord[Long, Bytes](kafkaTopic, time, data)
		producer.send(r)
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
	def factory(brokers: Seq[String]): Factory = {
		val props = new Properties()
		props.put("bootstrap.servers", brokers.mkString(","))
		props.put("client.id", getClass.getSimpleName)
		props.put("key.serializer", classOf[LongSerializer])
		props.put("value.serializer", classOf[NopSerializer])

		val producer = new KafkaProducer[Long, Bytes](props)
		new Factory(producer)
	}

	class Factory(producer: KafkaProducer[Long, Bytes]) extends Channel.Factory
	{
		def channel(kafkaTopic: String): Channel = new KafkaChannel(kafkaTopic, producer)

		/**
		  * This has to be called when the channels are not needed anymore.
		  */
		def close(): Unit = {
			producer.close()
		}
	}
}