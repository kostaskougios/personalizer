package com.akt.personalizer.dao

import javax.inject.{Inject, Singleton}

import scala.concurrent.Future

@Singleton
class DatabaseDao @Inject()(daoDependencies: DaoDependencies)
{

	import daoDependencies._
	import tables.profile.api._

	def createSchema(): Future[Unit] = executor.executeSchemaActions(
		Seq(
			tables.schema.create
		)
	)

	def dropSchema(): Future[Unit] = executor.executeSchemaActions(
		Seq(
			tables.schema.drop
		)
	)
}
