package com.mysn.personalizer.tables

import com.aktit.avro.{AvroVersionedSerdes, VersionedSerializers}
import com.aktit.personalizer.model.TableDef

/**
  * @author kostas.kougios
  *         08/06/18 - 15:02
  */
object View extends TableDef[v1.View]
{
	override def name = "social.View"

	override def serdes = VersionedSerializers(Nil, AvroVersionedSerdes(1))

	def row = v1.View.apply _
}
