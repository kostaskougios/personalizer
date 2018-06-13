package com.akt.personalizer.dao

import java.util.UUID

import com.aktit.personalizer.dao.slickgenerated.Tables
import com.aktit.personalizer.di.{Destroy, GuiceFactory}
import com.google.inject.AbstractModule
import net.codingwell.scalaguice.InjectorExtensions.ScalaInjector
import net.codingwell.scalaguice.ScalaModule
import org.scalatest.concurrent.{IntegrationPatience, ScalaFutures}
import org.scalatest.{BeforeAndAfterAll, FunSuite, Matchers}

import scala.util.Try

abstract class AbstractDaoTest extends FunSuite with Matchers with BeforeAndAfterAll with ScalaFutures with IntegrationPatience
{

	val driver = slick.jdbc.H2Profile
	val databaseConfig = DatabaseConfig("jdbc:h2:mem:" + UUID.randomUUID.toString, "org.h2.Driver", profile = driver)
	val tables = new Tables
	{
		override val profile = driver
	}

	protected def extraModules: Seq[AbstractModule with ScalaModule] = Nil

	private val modules = Seq(
		new DaoModule(databaseConfig)
	) ++ extraModules

	private val guiceFactory = GuiceFactory(modules: _*)
	val injector: ScalaInjector = guiceFactory.injector
	val databaseDao = injector.instance[DatabaseDao]
	databaseDao.createSchema().futureValue

	override protected def afterAll(): Unit = {
		modules.collect {
			case d: Destroy => d
		}.foreach(module => Try(module.destroy()))
		guiceFactory.destroy()
	}
}
