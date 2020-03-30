package com.aktit.personalizer.model.time

import java.time.{ZoneOffset, ZonedDateTime}

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers._

/**
  * @author kostas.kougios
  *         11/06/18 - 15:48
  */
class TimeSplitterTest extends AnyFunSuite {
  test("hour divider") {
    val t = ZonedDateTime.of(2010, 8, 1, 23, 50, 44, 232, ZoneOffset.UTC)
    val divided = TimeSplitter.ByHour.divideTime(t.toInstant.toEpochMilli)
    val rounded = TimeSplitter.ByHour.toRoundedUTCDateTime(divided)
    rounded should be(ZonedDateTime.of(2010, 8, 1, 23, 0, 0, 0, ZoneOffset.UTC))
  }
}
