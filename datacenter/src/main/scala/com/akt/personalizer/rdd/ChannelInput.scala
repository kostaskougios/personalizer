package com.akt.personalizer.rdd

import com.aktit.avro.AvroVersionedSerdes

/**
  * @author kostas.kougios
  *         10/06/18 - 20:14
  */
case class ChannelInput(time: Long, data: Array[Byte])

object ChannelInput
{
	val Serializer = AvroVersionedSerdes[ChannelInput](1)
}