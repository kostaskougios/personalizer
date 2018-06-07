package com.aktit.personalizer.service

import com.aktit.avro.AvroVersionedSerdes
import com.aktit.personalizer.channels.Channel

/**
  * Thread safe producer, use 1 instance per TABLE per jvm
  *
  * @author kostas.kougios
  *         07/06/18 - 17:51
  */
class ProducerService[TABLE] private(channel: Channel, latestVersionSerdes: AvroVersionedSerdes[TABLE])
{

	def produce(row: TABLE): Unit = {
		val data = latestVersionSerdes.serializeOne(row)
		channel.send(data)
	}

}

object ProducerService
{
	def apply[TABLE](channel: Channel, allSerdes: Seq[AvroVersionedSerdes[TABLE]]): ProducerService[TABLE] = {
		// we always produce using the latest version
		val serdes = allSerdes.maxBy(_.version)
		apply(channel, serdes)

	}

	def apply[TABLE](channel: Channel, latestVersionSerdes: AvroVersionedSerdes[TABLE]): ProducerService[TABLE] = {
		new ProducerService(channel, latestVersionSerdes)
	}
}