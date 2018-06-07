package com.aktit.personalizer.model

import java.time.ZonedDateTime

import com.aktit.personalizer.model.stats.UserCounter

/**
  * @author kostas.kougios
  *         07/06/18 - 12:44
  */
trait Observation
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

	def apply(user: User, time: ZonedDateTime, detail: Detail): Observation = SingleObservation(user, time, detail)

	def apply(user: User, time: ZonedDateTime, details: Seq[Detail]): Observation = MultiObservation(user, time, details)

	private case class SingleObservation(user: User, time: ZonedDateTime, detail: Detail) extends Observation
	{
		override def details = Seq(detail)
	}

	private case class MultiObservation(user: User, time: ZonedDateTime, details: Seq[Observation.Detail]) extends Observation
}