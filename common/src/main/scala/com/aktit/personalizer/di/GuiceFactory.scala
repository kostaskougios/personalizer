package com.aktit.personalizer.di

import com.google.inject.{AbstractModule, Guice}
import net.codingwell.scalaguice.InjectorExtensions.ScalaInjector
import net.codingwell.scalaguice.ScalaModule

class GuiceFactory private(modules: Seq[AbstractModule with ScalaModule])
{
	val injector: ScalaInjector = new ScalaInjector(Guice.createInjector(modules.map(_.asInstanceOf[AbstractModule]): _*))

	def destroy(): Unit = {
		modules.collect {
			case d: Destroy => d
		}.foreach(_.destroy())
	}
}

object GuiceFactory
{
	def apply(modules: AbstractModule with ScalaModule*) = new GuiceFactory(modules)
}