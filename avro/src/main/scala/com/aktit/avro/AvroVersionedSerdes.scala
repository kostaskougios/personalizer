package com.aktit.avro

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}

import com.sksamuel.avro4s._

/**
  * @author kostas.kougios
  *         07/06/18 - 19:53
  */
class AvroVersionedSerdes[T: SchemaFor : ToRecord : FromRecord] private(val version: Byte)
{
	def serializeOne(t: T): Array[Byte] = serialize(Seq(t))

	def serialize(ts: Seq[T]): Array[Byte] = {
		val bos = new ByteArrayOutputStream(8192)
		bos.write(version)
		val aos = AvroOutputStream.binary[T](bos)
		try ts.foreach(t => aos.write(t)) finally aos.close()
		bos.close()
		bos.toByteArray
	}

	def deserializeOne(a: Array[Byte]): T = deserialize(a).head

	def deserialize(a: Array[Byte]): Seq[T] = {
		val in = new ByteArrayInputStream(a)
		val v = in.read.toByte
		if (v != version) throw new IllegalArgumentException(s"I am a serdes for version $version but the data are for version $v")

		val ais = AvroInputStream.binary[T](in)
		try ais.iterator.toSeq finally ais.close()
	}
}

object AvroVersionedSerdes
{
	def apply[T: SchemaFor : ToRecord : FromRecord](version: Byte) = new AvroVersionedSerdes[T](version)
}