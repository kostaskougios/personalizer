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
	lazy val schema: profile.SchemaDescription = DirectoryStatus.schema

	@deprecated("Use .schema instead of .ddl", "3.0")
	def ddl = schema

	/** Entity class storing rows of table DirectoryStatus
	  *
	  * @param id     Database column id SqlType(int4), PrimaryKey
	  * @param path   Database column path SqlType(text)
	  * @param status Database column status SqlType(int2) */
	case class DirectoryStatusRow(id: Int, path: String, status: Short)

	/** GetResult implicit for fetching DirectoryStatusRow objects using plain SQL queries */
	implicit def GetResultDirectoryStatusRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Short]): GR[DirectoryStatusRow] = GR {
		prs =>
			import prs._
			DirectoryStatusRow.tupled((<<[Int], <<[String], <<[Short]))
	}

	/** Table description of table directory_status. Objects of this class serve as prototypes for rows in queries. */
	class DirectoryStatus(_tableTag: Tag) extends profile.api.Table[DirectoryStatusRow](_tableTag, "directory_status")
	{
		def * = (id, path, status) <> (DirectoryStatusRow.tupled, DirectoryStatusRow.unapply)

		/** Maps whole row to an option. Useful for outer joins. */
		def ? = (Rep.Some(id), Rep.Some(path), Rep.Some(status)).shaped.<>({ r => import r._; _1.map(_ => DirectoryStatusRow.tupled((_1.get, _2.get, _3.get))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))

		/** Database column id SqlType(int4), PrimaryKey */
		val id: Rep[Int] = column[Int]("id", O.PrimaryKey)
		/** Database column path SqlType(text) */
		val path: Rep[String] = column[String]("path")
		/** Database column status SqlType(int2) */
		val status: Rep[Short] = column[Short]("status")
	}

	/** Collection-like TableQuery object for table DirectoryStatus */
	lazy val DirectoryStatus = new TableQuery(tag => new DirectoryStatus(tag))
}
