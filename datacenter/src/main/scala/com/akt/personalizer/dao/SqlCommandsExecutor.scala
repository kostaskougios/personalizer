package com.akt.personalizer.dao

import javax.inject.{Inject, Named, Singleton}
import slick.jdbc.JdbcBackend.DatabaseDef
import slick.jdbc.{JdbcProfile, TransactionIsolation}
import slick.sql.SqlAction

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class SqlCommandsExecutor @Inject()(
	val db: DatabaseDef,
	driver: JdbcProfile
)(@Named("dao") implicit private val ec: ExecutionContext)
{

	import driver.api._

	def begin: SqlCommands = new SqlCommands(this, Nil)

	def begin(data: Act): SqlCommands = new SqlCommands(this, Seq(data))

	def begin(data: Seq[Act]): SqlCommands = new SqlCommands(this, data)

	def transaction(action: Act): Future[ExecutionResult] =
		transaction(Seq(action))

	def transaction(data: Seq[Act]): Future[ExecutionResult] = {
		val all = DBIO.sequence(data)
			.transactionally
			.withTransactionIsolation(SqlCommandsExecutor.IsolationLevel)

		db.run(all).map(ExecutionResult)
	}

	def nonTransactionally(data: Seq[Act]): Future[ExecutionResult] = {
		val all = DBIO.sequence(data)
		db.run(all).map(ExecutionResult)
	}

	def executeSchemaActions(data: Seq[SqlAction[Unit, NoStream, Effect.Schema]]): Future[Unit] = {
		val all = DBIO.sequence(data)
			.transactionally
			.withTransactionIsolation(TransactionIsolation.Serializable)
		db.run(all).map(_ => ())
	}

	def query[ROW](q: Query[_, ROW, Seq]): Future[Seq[ROW]] = db.run(q.result)

}

object SqlCommandsExecutor
{
	val IsolationLevel = TransactionIsolation.ReadUncommitted
}