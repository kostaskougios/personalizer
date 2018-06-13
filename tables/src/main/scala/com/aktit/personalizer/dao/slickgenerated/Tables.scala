package com.aktit.personalizer.dao.slickgenerated

// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends
	{
		val profile = slick.jdbc.PostgresProfile
	} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables
{
	val profile: slick.jdbc.JdbcProfile

	import profile.api._
	// NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
	import slick.jdbc.{GetResult => GR}

	/** DDL for all tables. Call .create to execute. */
	lazy val schema: profile.SchemaDescription = Status.schema

	@deprecated("Use .schema instead of .ddl", "3.0")
	def ddl = schema

	/** Entity class storing rows of table Status
	  *
	  * @param path   Database column path SqlType(text)
	  * @param status Database column status SqlType(int2) */
	case class StatusRow(path: String, status: Short)

	/** GetResult implicit for fetching StatusRow objects using plain SQL queries */
	implicit def GetResultStatusRow(implicit e0: GR[String], e1: GR[Short]): GR[StatusRow] = GR {
		prs =>
			import prs._
			StatusRow.tupled((<<[String], <<[Short]))
	}

	/** Table description of table status. Objects of this class serve as prototypes for rows in queries. */
	class Status(_tableTag: Tag) extends profile.api.Table[StatusRow](_tableTag, "status")
	{
		def * = (path, status) <> (StatusRow.tupled, StatusRow.unapply)

		/** Maps whole row to an option. Useful for outer joins. */
		def ? = (Rep.Some(path), Rep.Some(status)).shaped.<>({ r => import r._; _1.map(_ => StatusRow.tupled((_1.get, _2.get))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))

		/** Database column path SqlType(text) */
		val path: Rep[String] = column[String]("path")
		/** Database column status SqlType(int2) */
		val status: Rep[Short] = column[Short]("status")
	}

	/** Collection-like TableQuery object for table Status */
	lazy val Status = new TableQuery(tag => new Status(tag))
}
