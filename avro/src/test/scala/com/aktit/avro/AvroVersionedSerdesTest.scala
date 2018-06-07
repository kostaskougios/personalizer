package com.aktit.avro

import com.aktit.avro.testdtos.{XV1, XV2}
import org.apache.avro.Schema
import org.scalatest.FunSuite
import org.scalatest.Matchers._

/**
  * @author kostas.kougios
  *         07/06/18 - 20:10
  */
class AvroVersionedSerdesTest extends FunSuite
{
	val serdesV1 = new AvroVersionedSerdes[XV1](1, Map.empty)

	// rename XV1 to XV2
	private val schemaV1 = new Schema.Parser().parse(
		"""
		  |{
		  |  "type" : "record",
		  |  "name" : "XV2",
		  |  "namespace" : "com.aktit.avro.testdtos",
		  |  "fields" : [ {
		  |    "name" : "i",
		  |    "type" : "int"
		  |  }, {
		  |    "name" : "s",
		  |    "type" : "string"
		  |  } ]
		  |}
		""".stripMargin)
	val serdesV2 = new AvroVersionedSerdes[XV2](2, Map(
		1.toByte -> schemaV1
	))

	val xv1 = XV1(8, "v1")
	val xv2 = XV2(8, 5, "v1")

	test("ser/des V1") {
		val data = serdesV1.serializeOne(xv1)
		serdesV1.deserializeOne(data) should be(xv1)
	}

	test("ser/des V2") {
		val data = serdesV2.serializeOne(xv2)
		serdesV2.deserializeOne(data) should be(xv2)
	}

	test("serialize V1 and deserialize V2") {
		val data = serdesV1.serializeOne(xv1)
		serdesV2.deserializeOne(data) should be(XV2(8, 0 /* j is missing in v1 */ , "v1"))
	}

}
