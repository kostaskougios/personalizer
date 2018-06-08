package com.mysn.personalizer.tables.v1

/**
  * @author kostas.kougios
  *         07/06/18 - 22:55
  */
case class Post private[tables](
	userId: Long,
	title: String,
	content: String
)