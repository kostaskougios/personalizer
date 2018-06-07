package com.aktit.avro

import org.apache.commons.lang3.SerializationUtils
import org.scalatest.FunSuite
import org.scalatest.Matchers._

/**
  * @author kostas.kougios
  *         05/06/18 - 20:46
  */
class AvroSerializerTest extends FunSuite
{

	val x1 = XV1(5, "hello")
	val x2 = XV1(6, "world")

	val serializerV1 = new AvroSerializer[XV1]
	val serializerV2 = new AvroSerializer[XV2]

	test("serialize/deserialize binary") {
		val data = serializerV1.serializeSingleBinary(x1)
		serializerV1.deserializeSingleBinary(data) should be(x1)
	}

	test("serialize/deserialize with schema") {
		val data = serializerV1.serializeWithSchema(Seq(x1, x2))
		serializerV1.deserializeWithSchema(data) should be(Seq(x1, x2))
	}

	test("bytesize") {
		val avroSz = serializerV1.serializeSingleBinary(x1).length
		val javaSz = SerializationUtils.serialize(x1).length
		avroSz should be < javaSz
	}

	test("schema compatibility") {
		val data = serializerV1.serializeWithSchema(Seq(x1))
		serializerV2.deserializeWithSchema(data) should be(Seq(XV2(5, 0, "hello")))
	}
}

case class XV1(i: Int, s: String)

case class XV2(i: Int, j: Int, s: String)
