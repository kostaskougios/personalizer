package com.akt.personalizer.files

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

import com.aktit.personalizer.model.TableDef

/**
  * @author kostas.kougios
  *         10/06/18 - 23:26
  */
object Directories
{
	def incomingDataDirectory(dataDir: String): String = dataDir + "/incoming"

	private val formatter = DateTimeFormatter.ISO_DATE_TIME

	def incomingDataDirectory(dataDir: String, tableDef: TableDef[_], zonedDateTime: ZonedDateTime): String =
		incomingDataDirectory(dataDir) + "/" + tableDef.incomingDataDirName + "/" + zonedDateTime.format(formatter).replace(':', '_') + "/" + UUID.randomUUID.toString
}
