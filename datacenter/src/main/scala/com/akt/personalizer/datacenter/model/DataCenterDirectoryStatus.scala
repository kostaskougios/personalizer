package com.akt.personalizer.datacenter.model

/**
  * @author kostas.kougios
  *         13/06/18 - 01:25
  */
case class DataCenterDirectoryStatus(path: String, status: DataCenterDirectoryStatus.Status)

object DataCenterDirectoryStatus
{

	trait Status
	{
		def id: Int
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
}
