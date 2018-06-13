package com.akt.personalizer.dao.crud

import slick.lifted.Query

import scala.concurrent.Future

trait Retrieve[A] extends ToDomain[A] with Dependencies
{
	type ROW

	def retrieveSql(id: Int): Query[_, ROW, Seq]

	import daoDependencies._

	def retrieve(id: Int): Future[Option[A]] = {
		val q = retrieveSql(id)
		for {
			r <- executor.query(q)
		} yield if (r.isEmpty) None
		else if (r.lengthCompare(1) == 0) Some(toDomain(r).head)
		else throw new IllegalStateException(s"1 row was expected for query $q when id is $id, but instead got $r")
	}

	def retrieveExisting(id: Int): Future[A] = retrieve(id).map(_.getOrElse(throw new IllegalStateException(s"there is no row with id $id")))
}
