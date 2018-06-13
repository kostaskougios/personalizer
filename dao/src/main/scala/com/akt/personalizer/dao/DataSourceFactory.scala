package com.akt.personalizer.dao

import com.mchange.v2.c3p0.ComboPooledDataSource
import javax.sql.DataSource
import org.slf4j.LoggerFactory

object DataSourceFactory
{
	private val log = LoggerFactory.getLogger(getClass)

	def maxConnections = 32

	def c3p0(databaseConfig: DatabaseConfig): DataSource = {
		log.info(s"Initializing c3p0 datasource, connecting to ${databaseConfig.url}")
		val dataSource = new ComboPooledDataSource
		dataSource.setDriverClass(databaseConfig.driver) //loads the jdbc driver
		dataSource.setJdbcUrl(databaseConfig.url)
		dataSource.setUser(databaseConfig.user)
		dataSource.setPassword(databaseConfig.password)
		dataSource.setMinPoolSize(1)
		dataSource.setMaxPoolSize(maxConnections)
		dataSource.setMaxIdleTime(10)
		dataSource.setMaxStatementsPerConnection(256)
		dataSource.setPreferredTestQuery("select 1")
		dataSource
	}

	def destroy(dataSource: DataSource) {
		dataSource match {
			case c: ComboPooledDataSource =>
				log.info(s"Destroying c3p0 datasource ${c.getDataSourceName}")
				c.close()
		}
	}
}
