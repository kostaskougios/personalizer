package com.akt.personalizer.datacenter.model

import com.akt.personalizer.dao.Ids

/**
  * @author kostas.kougios
  *         13/06/18 - 11:39
  */
object DataCenterModelBuilders
{
	def directoryStatus(
		id: Int = Ids.nextId,
		path: String = "/path",
		status: DirectoryStatus.Status = DirectoryStatus.Ready
	) = DirectoryStatus(id, path, status)
}
