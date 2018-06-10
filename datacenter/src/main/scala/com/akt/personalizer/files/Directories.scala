package com.akt.personalizer.files

import com.aktit.personalizer.model.TableDef

/**
  * @author kostas.kougios
  *         10/06/18 - 23:26
  */
object Directories
{
	def incomingDataDirectory(dataDir: String): String = dataDir + "/incoming"

	def incomingDataDirectory(dataDir: String, tableDef: TableDef[_]): String =
		incomingDataDirectory(dataDir) + "/" + tableDef.incomingDataDirName

	def incomingDataNowDirectory(dataDir: String, tableDef: TableDef[_]): String =
		incomingDataDirectory(dataDir, tableDef) + "/" + System.currentTimeMillis
}
