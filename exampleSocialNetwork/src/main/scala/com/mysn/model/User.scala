package com.mysn.model

import java.time.ZonedDateTime

/**
  * @author kostas.kougios
  *         07/06/18 - 17:39
  */
case class User(
	id: Long,
	name: String,
	dob: ZonedDateTime,
	friends: Seq[User]
)