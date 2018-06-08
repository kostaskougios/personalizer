package com.aktit.personalizer.model.time

import java.time.ZonedDateTime

/**
  * @author kostas.kougios
  *         08/06/18 - 14:50
  */
case class UTCDateTime(time: Long)

object UTCDateTime
{
	def apply(z: ZonedDateTime): UTCDateTime = UTCDateTime(z.toInstant.toEpochMilli)

	def now: UTCDateTime = UTCDateTime(System.currentTimeMillis)
}