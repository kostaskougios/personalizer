package com.akt.personalizer.datacenter.dao

import com.akt.personalizer.dao.DaoDependencies
import com.akt.personalizer.dao.crud.CRUD
import com.akt.personalizer.datacenter.model.DirectoryStatus
import javax.inject.{Inject, Singleton}

/**
  * @author kostas.kougios
  *         13/06/18 - 11:16
  */
@Singleton
class DirectoryStatusDao @Inject()(
	protected val daoDependencies: DaoDependencies
) extends CRUD[DirectoryStatus]
{

	import daoDependencies._
	import tables.profile.api._

	type ROW = tables.DirectoryStatusRow

	override def createSql(s: DirectoryStatus) = executor.begin(
		tables.DirectoryStatus += tables.DirectoryStatusRow(s.id, s.path, s.status.id)
	)

	override def retrieveSql(id: Int) = tables.DirectoryStatus.filter(_.id === id)

	override def deleteSql(id: Int) = executor.begin(
		retrieveSql(id).delete
	)

	override protected def toDomain(rows: Seq[ROW]) = rows.map {
		row =>
			DirectoryStatus(row.id, row.path, DirectoryStatus.StatusesById(row.status))
	}
}
