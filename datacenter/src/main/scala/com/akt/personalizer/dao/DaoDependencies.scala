package com.akt.personalizer.dao

import com.aktit.personalizer.dao.slickgenerated.Tables
import javax.inject.{Inject, Named, Singleton}

import scala.concurrent.ExecutionContext

@Singleton
class DaoDependencies @Inject()(
	val executor: SqlCommandsExecutor,
	val tables: Tables,
	@Named("dao") implicit val ec: ExecutionContext
)
