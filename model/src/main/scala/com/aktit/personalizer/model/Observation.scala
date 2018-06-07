package com.aktit.personalizer.model

import java.time.ZonedDateTime

import com.aktit.personalizer.model.serialization.ToBytes
import com.aktit.personalizer.model.stats.UserCounter

/**
  * @author kostas.kougios
  *         07/06/18 - 12:44
  */
trait Observation extends ToBytes
{
	// This observation is for this user
	def user: User

	// The time the observation occurred
	def time: ZonedDateTime

	def details: Seq[Observation.Detail]
}

object Observation
{

	trait Detail

	case class IncreaseCounter(counter: UserCounter) extends Detail

}