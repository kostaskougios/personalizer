package com.akt.personalizer.dao

import com.akt.personalizer.dao.crud.CRUD
import com.aktit.personalizer.model.Id

trait CRUDDaoTest[A <: Id]
{
	this: AbstractDaoTest =>

	def dao: CRUD[A]

	def createDomainModel: A

	test("create/retrieve") {
		val a = createDomainModel
		dao.create(a).futureValue
		dao.retrieveExisting(a.id).futureValue should be(a)
	}

	test("delete") {
		val a = createDomainModel
		dao.create(a).futureValue
		dao.delete(a).futureValue should be(true)
		dao.retrieve(a.id).futureValue should be(None)
	}
}
