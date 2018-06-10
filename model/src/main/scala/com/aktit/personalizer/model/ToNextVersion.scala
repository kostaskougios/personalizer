package com.aktit.personalizer.model

/**
  * @author kostas.kougios
  *         10/06/18 - 17:32
  */
trait ToNextVersion[A]
{
	def toNextVersion: A
}
