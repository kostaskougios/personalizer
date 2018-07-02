package com.akt.personalizer.dao

import com.aktit.personalizer.dao.slickgenerated.Tables
import com.aktit.personalizer.di.Destroy
import com.aktit.personalizer.utils.ExecutionContextFactory
import com.google.inject.AbstractModule
import com.google.inject.name.Names
import javax.sql.DataSource
import net.codingwell.scalaguice.ScalaModule
import org.slf4j.LoggerFactory
import slick.jdbc.JdbcBackend.Database
import slick.jdbc.JdbcProfile
import slick.util.AsyncExecutor

import scala.concurrent.ExecutionContext

class DaoModule(databaseConfig: DatabaseConfig) extends AbstractModule with ScalaModule with Destroy
{
	private def threadPoolSize = DataSourceFactory.maxConnections

	private val (daoEcPool, daoEc) = ExecutionContextFactory.newFixedThreadPool("dao", threadPoolSize)
	private val dataSource = DataSourceFactory.c3p0(databaseConfig)
	private val slickExecutor = AsyncExecutor("Slick-Executor", threadPoolSize, 256)

	override def configure() = {
		LoggerFactory.getLogger(getClass).info("Initializing DaoModule")

		bind[ExecutionContext].annotatedWith(Names.named("dao")).toInstance(daoEc)
		bind[DataSource].toInstance(dataSource)
		bind[DatabaseConfig].toInstance(databaseConfig)
		bind[slick.jdbc.JdbcBackend.DatabaseDef].toInstance(
			Database.forDataSource(
				dataSource,
				Some(DataSourceFactory.maxConnections),
				slickExecutor
			)
		)
		bind[JdbcProfile].toInstance(databaseConfig.profile)
		bind[Tables].toInstance(new Tables
		{
			override val profile = databaseConfig.profile
		})
	}

	override def destroy(): Unit = {
		LoggerFactory.getLogger(getClass).info("Destroying DaoModule")
		DataSourceFactory.destroy(dataSource)
		daoEcPool.shutdown()
		slickExecutor.close()
	}
}
