package com.akt.personalizer.dao.crud

import com.akt.personalizer.dao.SqlCommands

import scala.concurrent.Future

trait Create[A] extends Dependencies
{

	import daoDependencies._

	def createSql(a: A): SqlCommands

	def create(a: A): Future[A] = {
		val sql = createSql(a)
		sql.commit().map {
			r =>
				if (r.hasAffectedRows)
					a
				else throw new IllegalStateException(s"No rows were affected when trying to create $a")
		}
	}
}
