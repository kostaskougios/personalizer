package com.aktit.personalizer.di

import net.codingwell.scalaguice.InjectorExtensions

import scala.collection.mutable
import scala.reflect.ClassTag

/**
  * @author kostas.kougios
  *         13/06/18 - 11:51
  */
object SparkExecutorGuiceInjector
{
	private val m = mutable.Map.empty[String, GuiceFactory]

	def get[INJ <: NamedInjector : ClassTag]: InjectorExtensions.ScalaInjector = synchronized {
		val clz = scala.reflect.classTag[INJ].runtimeClass.asInstanceOf[Class[INJ]]
		m.getOrElseUpdate(clz.getName, {
			val i = clz.newInstance
			GuiceFactory(i.modules: _*)
		}).injector
	}
}
