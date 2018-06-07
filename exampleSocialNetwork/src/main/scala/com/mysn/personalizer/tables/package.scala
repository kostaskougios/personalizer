package com.mysn.personalizer

import com.aktit.avro.AvroVersionedSerdes

/**
  * @author kostas.kougios
  *         07/06/18 - 22:53
  */
package object tables
{
	// the latest table version
	type Post = v2.Post
	// All table versions. We can drop the table versions that we no more have serialized data
	val PostSerializers = Seq(
		AvroVersionedSerdes[v1.Post](1),
		AvroVersionedSerdes[v2.Post](2)
	)
}
