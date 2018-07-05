package com.akt.personalizer.datacenter.rdd

import com.akt.personalizer.datacenter.dao.DirectoryStatusDao
import com.aktit.personalizer.di.GuiceFactory
import org.apache.spark.internal.Logging

/**
  * Beans that live on the spark driver
  *
  * @author kostas.kougios
  *         05/07/18 - 15:47
  */
class DriverGuice(guiceFactory: GuiceFactory) extends Logging
{
	logInfo("Initializing DriverGuice")

	val directoryStatusDao = guiceFactory.injector.instance[DirectoryStatusDao]
}

object DriverGuice
{
	def apply(guiceFactory: GuiceFactory) = new DriverGuice(guiceFactory)
}