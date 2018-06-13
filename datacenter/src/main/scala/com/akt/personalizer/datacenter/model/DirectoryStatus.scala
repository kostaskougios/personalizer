package com.akt.personalizer.datacenter.model

import com.aktit.personalizer.model.Id

/**
  * @author kostas.kougios
  *         13/06/18 - 01:25
  */
case class DirectoryStatus(
	id: Int,
	path: String,
	status: DirectoryStatus.Status
) extends Id

object DirectoryStatus
{

	trait Status
	{
		def id: Short
	}

	object UnderCreation extends Status
	{
		override def id = 1
	}

	object Ready extends Status
	{
		override def id = 2
	}

	object CanBeDeleted extends Status
	{
		override def id = 3
	}

	val Statuses = Seq(UnderCreation, Ready, CanBeDeleted)
	val StatusesById = Statuses.map(s => (s.id, s)).toMap
}
