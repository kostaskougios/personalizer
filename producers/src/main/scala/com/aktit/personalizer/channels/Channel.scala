package com.aktit.personalizer.channels

/**
  * @author kostas.kougios
  *         07/06/18 - 23:38
  */
trait Channel
{
	def send(o: Array[Byte]): Unit
}
