package com.aktit.personalizer.model.time

import java.time.{Instant, ZoneOffset, ZonedDateTime}

/**
  * @author kostas.kougios
  *         11/06/18 - 15:26
  */
abstract class TimeSplitter private(protected val divider: Long) extends Serializable
{
	def divideTime(t: Long): Long = t / divider

	def toRoundedUTCDateTime(dividedTime: Long): ZonedDateTime
}

object TimeSplitter
{
	private val HourDivider = 1000 * 60 * 60

	val ByHour: TimeSplitter = new TimeSplitter(HourDivider)
	{
		override def toRoundedUTCDateTime(dividedTime: Long) = {
			val i = Instant.ofEpochMilli(dividedTime * divider)
			ZonedDateTime.ofInstant(i, ZoneOffset.UTC)
		}
	}
}