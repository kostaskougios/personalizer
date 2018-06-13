package com.akt.personalizer.dao

import java.util.concurrent.atomic.AtomicInteger

/**
  * @author kostas.kougios
  *         13/06/18 - 11:40
  */
object Ids
{
	private val id = new AtomicInteger

	def nextId = id.incrementAndGet()
}
