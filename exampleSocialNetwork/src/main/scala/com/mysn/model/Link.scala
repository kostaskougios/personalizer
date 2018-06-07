package com.mysn.model

/**
  * @author kostas.kougios
  *         07/06/18 - 22:52
  */
sealed trait Link
{
	def url: String
}

case class InternalLink(url: String) extends Link

case class ExternalLink(url: String) extends Link
