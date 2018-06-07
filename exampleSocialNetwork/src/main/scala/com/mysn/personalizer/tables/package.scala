package com.mysn.personalizer

import com.aktit.avro.AvroVersionedSerdes

/**
  * @author kostas.kougios
  *         07/06/18 - 22:53
  */
package object tables
{
	type Post = v2.Post
	val PostSerializer = Seq(AvroVersionedSerdes[v1.Post](1), AvroVersionedSerdes[v2.Post](2))
}
