package com.aktit.personalizer.channels.kafka

import java.util

import com.aktit.personalizer.channels.Bytes
import org.apache.kafka.common.serialization.Serializer

/**
  * @author kostas.kougios
  *         07/06/18 - 18:20
  */
class NopSerializer extends Serializer[Bytes]
{
	override def configure(configs: util.Map[String, _], isKey: Boolean): Unit = {}

	override def close(): Unit = {}

	override def serialize(s: String, t: Bytes): Array[Byte] = t
}
