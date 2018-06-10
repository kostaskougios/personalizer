package com.mysn.personalizer.tables.v1

import com.aktit.personalizer.model.ToNextVersion
import com.mysn.personalizer.tables.v2

/**
  * @author kostas.kougios
  *         07/06/18 - 22:55
  */
case class Post private[tables](
	userId: Long,
	title: String,
	content: String
) extends ToNextVersion[v2.Post]
{
	override def toNextVersion = v2.Post(userId, title, Some(content), None)
}