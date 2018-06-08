package com.mysn.personalizer.tables.v2

/**
  * @author kostas.kougios
  *         07/06/18 - 22:55
  */
case class Post private[tables](
	userId: Long,
	title: String,
	content: Option[String],
	link: Option[String]
)
