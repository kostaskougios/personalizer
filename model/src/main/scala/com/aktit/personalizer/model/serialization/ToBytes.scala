package com.aktit.personalizer.model.serialization

/**
  * @author kostas.kougios
  *         07/06/18 - 13:01
  */
trait ToBytes
{
	def toBytes: Array[Byte]
}
