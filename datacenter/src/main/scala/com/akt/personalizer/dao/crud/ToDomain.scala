package com.akt.personalizer.dao.crud

trait ToDomain[A]
{
	type ROW

	protected def toDomain(rows: Seq[ROW]): Seq[A]
}
