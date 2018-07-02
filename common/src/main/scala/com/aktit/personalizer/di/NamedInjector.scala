package com.aktit.personalizer.di

import com.google.inject.AbstractModule
import net.codingwell.scalaguice.ScalaModule

/**
  * @author kostas.kougios
  *         13/06/18 - 12:05
  */
trait NamedInjector
{
	def modules: Seq[AbstractModule with ScalaModule]
}
