package com.aktit.personalizer.channels

import java.util

import org.apache.kafka.common.serialization.Serializer

/**
  * @author kostas.kougios
  *         07/06/18 - 18:20
  */
class RowSerializer extends Serializer[AnyRef]
{
	override def configure(configs: util.Map[String, _], isKey: Boolean): Unit = {}

	override def serialize(topic: String, data: AnyRef): Array[Byte] = {
		???
	}

	override def close(): Unit = {}
}
