package com.aktit.personalizer.model.time

import java.time.{ZoneOffset, ZonedDateTime}

import org.scalatest.FunSuite
import org.scalatest.Matchers._

/**
  * @author kostas.kougios
  *         08/06/18 - 14:56
  */
class UTCDateTimeTest extends FunSuite
{
	test("millis on different time zones") {
		val d1 = UTCDateTime(ZonedDateTime.of(2000, 5, 21, 20, 10, 30, 0, ZoneOffset.UTC))
		val d2 = UTCDateTime(ZonedDateTime.of(2000, 5, 21, 22, 10, 30, 0, ZoneOffset.ofHours(2)))
		d1 should be(d2)
	}
}
