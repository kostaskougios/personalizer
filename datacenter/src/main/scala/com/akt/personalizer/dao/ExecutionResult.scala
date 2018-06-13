package com.akt.personalizer.dao

case class ExecutionResult(rowsAffected: Seq[Int])
{
	def rowsAffectedCount: Int = rowsAffected.sum

	def hasAffectedRows: Boolean = rowsAffectedCount > 0
}