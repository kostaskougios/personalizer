package com.akt.personalizer.datacenter.rdd

import com.aktit.avro.AvroVersionedSerdes
import com.aktit.personalizer.model.TableDef

/**
  * @author kostas.kougios
  *         10/06/18 - 20:14
  */
case class ChannelInput(time: Long, data: Array[Byte])
{
	def deserialize[TABLE](tableDef: TableDef[TABLE]) = tableDef.serdes.deserializeOneAnyVersion(data)
}

object ChannelInput
{
	val Serializer = AvroVersionedSerdes[ChannelInput](1)
}