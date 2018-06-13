package com.akt.personalizer.dao

/**
  * @author kostas.kougios
  *         13/06/18 - 01:07
  */
case class DatabaseConfig(
	url: String = "jdbc:postgresql://localhost/myexpenses",
	driver: String = "org.postgresql.Driver",
	user: String = "myexpenses",
	password: String = "123",
	profile: slick.jdbc.JdbcProfile = slick.jdbc.PostgresProfile
)
