package com.aktit.personalizer.producers

import com.aktit.avro.AvroVersionedSerdes
import com.aktit.personalizer.channels.Channel
import com.aktit.personalizer.model.TableDef

/**
  * Thread safe producer, use 1 instance per TABLE per jvm
  *
  * @author kostas.kougios
  *         07/06/18 - 17:51
  */
class Producer[TABLE] private(channel: Channel, latestVersionSerdes: AvroVersionedSerdes[TABLE])
{

	def produce(row: TABLE): Unit = {
		val data = latestVersionSerdes.serializeOne(row)
		channel.send(data)
	}

}

object Producer
{
	def factory(channelFactory: Channel.Factory) = new Factory(channelFactory)

	class Factory(channelFactory: Channel.Factory)
	{
		def producer[TABLE](tableDef: TableDef[TABLE]): Producer[TABLE] = {
			val serdes = tableDef.serdes.currentVersionSerializer
			new Producer(channelFactory.channel(tableDef.channelName), serdes)
		}
	}
}