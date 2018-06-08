package com.mysn.personalizer.tables

import com.aktit.avro.{AvroVersionedSerdes, VersionedSerializers}
import com.aktit.personalizer.model.TableDef

/**
  * @author kostas.kougios
  *         08/06/18 - 15:01
  */
object Post extends TableDef[v2.Post]
{
	override def name = "social.Post"

	// All table versions. We can drop the table versions that we no more have serialized data
	override def serdes = VersionedSerializers[v2.Post](
		Seq(AvroVersionedSerdes[v1.Post](1)),
		AvroVersionedSerdes[v2.Post](2)
	)

	// use this to create new instances of Post
	def row = v2.Post.apply _
}
