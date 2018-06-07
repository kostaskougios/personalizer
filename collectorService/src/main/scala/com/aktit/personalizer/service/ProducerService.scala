package com.aktit.personalizer.service

import java.util.Properties

import com.aktit.personalizer.model.Observation
import com.aktit.personalizer.service.kafka.ObservationSerializer
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.apache.kafka.common.serialization.IntegerSerializer

/**
  * Thread safe producer, use 1 instance per jvm
  *
  * @author kostas.kougios
  *         07/06/18 - 17:51
  */
class ProducerService(brokers: String, kafkaTopic: String)
{
	private val props = new Properties()
	props.put("bootstrap.servers", brokers)
	props.put("client.id", getClass.getSimpleName)
	props.put("key.serializer", classOf[IntegerSerializer])
	// We will store the data in kafka by serializing the Page class.
	props.put("value.serializer", classOf[ObservationSerializer])

	private val producer = new KafkaProducer[Long, Observation](props)

	def produce(o: Observation): Unit = {
		val r = new ProducerRecord[Long, Observation](kafkaTopic, o)
		producer.send(r)
	}

	/**
	  * This has to be called when the service is not needed anymore
	  */
	def destroy(): Unit = {
		producer.close()
	}
}
