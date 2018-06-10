package com.aktit.personalizer.model

import com.aktit.avro.VersionedSerializers

/**
  * Just extend this trait to define a table
  *
  * @author kostas.kougios
  *         08/06/18 - 13:33
  */
trait TableDef[TABLE]
{
	def name: String

	def channelName: String = name

	def incomingDataDirName = name

	def serdes: VersionedSerializers[TABLE]

	def latestVersionOf(a: Any): TABLE = a match {
		case to: ToNextVersion[_] => latestVersionOf(to.toNextVersion)
		case t: TABLE@unchecked => t
	}

	override def toString = s"TableDef($name)"
}
