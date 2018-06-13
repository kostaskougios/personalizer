package com.aktit.personalizer.utils

import java.util.concurrent.{ExecutorService, Executors}

import scala.concurrent.ExecutionContext

object ExecutionContextFactory
{
	def newFixedThreadPool(name: String, threadPoolSize: Int): (ExecutorService, ExecutionContext) = {
		val ecPool = Executors.newFixedThreadPool(threadPoolSize)
		val ec = new ExecutionContext
		{

			def execute(runnable: Runnable) {
				ecPool.submit(runnable)
			}

			def reportFailure(t: Throwable): Unit = {
				throw t
			}
		}

		(ecPool, ec)
	}
}
