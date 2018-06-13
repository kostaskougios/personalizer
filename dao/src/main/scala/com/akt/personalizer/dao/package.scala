package com.akt.personalizer

import slick.dbio.Effect.All
import slick.dbio.{Effect, NoStream}
import slick.sql.SqlAction

package object dao
{
	type Act = SqlAction[Int, NoStream, Effect.All]

	type DBAct = slick.dbio.DBIOAction[Int, NoStream, All]
}
