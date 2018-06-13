package com.akt.personalizer.dao

import scala.concurrent.Future

class SqlCommands(
	executor: SqlCommandsExecutor,
	val data: Seq[Act]
)
{
	def ++(other: SqlCommands): SqlCommands = new SqlCommands(executor, data ++ other.data)

	def ++(acts: Seq[Act]): SqlCommands = new SqlCommands(executor, data ++ acts)

	def commit(): Future[ExecutionResult] =
		executor.transaction(data)

}
