package com.aktit.personalizer.channels

/**
  * @author kostas.kougios
  *         07/06/18 - 23:38
  */
trait Channel
{
	def send(time: Long, data: Array[Byte]): Unit
}

object Channel
{

	trait Factory
	{
		def channel(name: String): Channel

		def close(): Unit
	}

}
