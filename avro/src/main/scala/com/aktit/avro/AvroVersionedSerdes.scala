package com.aktit.avro

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}

import com.sksamuel.avro4s._
import org.apache.avro.Schema

/**
  * @author kostas.kougios
  *         07/06/18 - 19:53
  */
class AvroVersionedSerdes[T: SchemaFor : ToRecord : FromRecord](
	currentVersion: Byte,
	versions: Map[Byte, Schema]
)
{
	def serializeOne(t: T): Array[Byte] = serialize(Seq(t))

	def serialize(ts: Seq[T]): Array[Byte] = {
		// always serialize with latest schema
		val bos = new ByteArrayOutputStream(8192)
		bos.write(currentVersion)
		val aos = AvroOutputStream.binary[T](bos)
		try ts.foreach(t => aos.write(t)) finally aos.close()
		bos.close()
		bos.toByteArray
	}

	def deserializeOne(a: Array[Byte]): T = deserialize(a).head

	def deserialize(a: Array[Byte]): Seq[T] = {
		val in = new ByteArrayInputStream(a)
		val version = in.read.toByte
		val ais = if (version == currentVersion) {
			AvroInputStream.binary[T](in)
		} else {
			val schema = versions(version)
			AvroInputStream.binary[T](in, schema)
		}
		try ais.iterator.toSeq finally ais.close()
	}
}
