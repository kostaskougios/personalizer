package com.aktit.personalizer.service.kafka

import java.util

import com.aktit.personalizer.model.Observation
import org.apache.kafka.common.serialization.Serializer

/**
  * @author kostas.kougios
  *         07/06/18 - 18:20
  */
class ObservationSerializer extends Serializer[Observation]
{
	override def configure(configs: util.Map[String, _], isKey: Boolean): Unit = {}

	override def serialize(topic: String, data: Observation): Array[Byte] = {
		???
	}

	override def close(): Unit = {}
}
