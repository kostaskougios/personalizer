package com.mysn.personalizer

import com.aktit.avro.{AvroVersionedSerdes, VersionedSerializers}
import com.aktit.personalizer.model.TableDef

/**
  * @author kostas.kougios
  *         07/06/18 - 22:53
  */
package object tables
{
	// the latest table version
	class PostDef extends TableDef[v2.Post]
	{
		override def name = "Post"

		// All table versions. We can drop the table versions that we no more have serialized data
		override def serdes = VersionedSerializers[v2.Post](
			Seq(AvroVersionedSerdes[v1.Post](1)),
			AvroVersionedSerdes[v2.Post](2)
		)

		def row = v2.Post.apply _
	}

	val Post = new PostDef
}
