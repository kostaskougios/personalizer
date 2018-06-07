package com.aktit.personalizer.model

import com.aktit.personalizer.model.serialization.ToBytes

/**
  * @author kostas.kougios
  *         07/06/18 - 12:44
  */
trait User extends ToBytes
{
	type Id

	def id: Id
}
