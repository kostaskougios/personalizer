package com.akt.personalizer.dao.crud

import com.akt.personalizer.dao.SqlCommands
import com.akt.personalizer.datacenter.model.Id

import scala.concurrent.Future

trait Delete[A <: Id] extends Dependencies
{

	import daoDependencies._

	def deleteSql(id: Int): SqlCommands

	def delete(id: Int): Future[Boolean] = deleteSql(id).commit().map(_.hasAffectedRows)

	def delete(a: A): Future[Boolean] = deleteSql(a.id).commit().map(_.hasAffectedRows)
}
