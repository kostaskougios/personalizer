package com.aktit.personalizer.model

/**
  * @author kostas.kougios
  *         07/06/18 - 12:44
  */
trait User
{
	type Id

	def id: Id
}
