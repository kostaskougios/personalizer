package com.akt.personalizer.consumers.kafka

import java.util

import org.apache.kafka.common.serialization.Deserializer

/**
  * @author kostas.kougios
  *         10/06/18 - 19:58
  */
private[kafka] class NoOpDeserializer extends Deserializer[Array[Byte]]
{
	override def configure(map: util.Map[String, _], b: Boolean): Unit = {}

	override def deserialize(topic: String, bytes: Array[Byte]): Array[Byte] = bytes

	override def close(): Unit = {}
}
