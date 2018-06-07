package com.aktit.avro

import com.aktit.avro.testdtos.{XColV1, XColV2, XV1, XV2}
import com.sksamuel.avro4s.SchemaFor
import org.scalatest.FunSuite
import org.scalatest.Matchers._

/**
  * @author kostas.kougios
  *         07/06/18 - 20:10
  */
class AvroVersionedSerdesTest extends FunSuite
{

	test("ser/des V1") {
		new XApp
		{
			val data = serdesV1.serializeOne(xv1)
			serdesV1.deserializeOne(data) should be(xv1)
		}
	}

	test("ser/des V2") {
		new XApp
		{
			val data = serdesV2.serializeOne(xv2)
			serdesV2.deserializeOne(data) should be(xv2)
		}
	}

	test("serialize V1 and deserialize V2") {
		new XApp
		{
			val data = serdesV1.serializeOne(xv1)
			serdesV2.deserializeOne(data) should be(XV2(8, 0 /* j is missing in v1 */ , "v1"))
		}
	}

	class XApp
	{
		val serdesV1 = new AvroVersionedSerdes[XV1](1, Map.empty)

		val serdesV2 = new AvroVersionedSerdes[XV2](2, Map(
			1.toByte -> SchemaFor[XV1].apply
		))

		val xv1 = XV1(8, "v1")
		val xv2 = XV2(8, 5, "v1")
	}

	test("ser/des ColV1") {
		new XColApp
		{
			val data = serdesV1.serializeOne(xv1)
			serdesV1.deserializeOne(data) should be(xv1)
		}
	}

	test("ser/des ColV2") {
		new XColApp
		{
			val data = serdesV2.serializeOne(xv2)
			serdesV2.deserializeOne(data) should be(xv2)
		}
	}

	test("serialize ColV1 and deserialize ColV2") {
		new XColApp
		{
			val data = serdesV1.serializeOne(xv1)
			serdesV2.deserializeOne(data) should be(XColV2(8, "v1", Nil))
		}
	}

	class XColApp
	{
		val serdesV1 = new AvroVersionedSerdes[XColV1](1, Map.empty)

		val serdesV2 = new AvroVersionedSerdes[XColV2](2, Map(
			1.toByte -> SchemaFor[XV1].apply
		))

		val xv1 = XColV1(8, "v1", Seq("hi", "there"))
		val xv2 = XColV2(8, "v1", Seq("the", "array"))
	}
}
