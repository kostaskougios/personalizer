package com.mysn.model

/**
  * @author kostas.kougios
  *         07/06/18 - 17:40
  */
sealed trait Post
{
	def from: User

	def title: String
}

case class QuickPost(from: User, title: String, content: String) extends Post

case class ShareLink(from: User, title: String, link: String) extends Post