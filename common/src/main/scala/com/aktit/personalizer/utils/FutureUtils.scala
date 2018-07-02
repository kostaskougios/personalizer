package com.aktit.personalizer.utils

import java.util.concurrent.TimeUnit

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

/**
  * @author kostas.kougios
  *         02/07/18 - 17:30
  */
object FutureUtils
{
	private val TenSeconds = Duration(10, TimeUnit.SECONDS)

	implicit class FutureEx[A](f: Future[A])
	{
		def await(atMost: Duration): A = Await.result(f, atMost)

		def await: A = Await.result(f, TenSeconds)
	}

}
