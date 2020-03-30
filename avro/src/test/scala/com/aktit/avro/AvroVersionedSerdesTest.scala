package com.aktit.avro

import com.aktit.avro.testdtos.{XV1, XV2}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers._

/**
  * @author kostas.kougios
  *         07/06/18 - 20:10
  */
class AvroVersionedSerdesTest extends AnyFunSuite {

  test("ser/des V1") {
    new XApp {
      val data = serdesV1.serializeOne(xv1)
      serdesV1.deserializeOne(data) should be(xv1)
    }
  }

  test("ser/des V2") {
    new XApp {
      val data = serdesV2.serializeOne(xv2)
      serdesV2.deserializeOne(data) should be(xv2)
    }
  }

  class XApp {
    val serdesV1 = AvroVersionedSerdes[XV1](1)

    val serdesV2 = AvroVersionedSerdes[XV2](2)

    val xv1 = XV1(8, "v1")
    val xv2 = XV2(8, 5, "v1")
  }

}
