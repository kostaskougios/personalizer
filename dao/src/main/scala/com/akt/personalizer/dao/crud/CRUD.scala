package com.akt.personalizer.dao.crud

import com.aktit.personalizer.model.Id

trait CRUD[A <: Id] extends Create[A] with Retrieve[A] with Delete[A]
