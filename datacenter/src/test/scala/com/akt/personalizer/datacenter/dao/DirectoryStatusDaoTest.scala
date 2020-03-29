package com.akt.personalizer.datacenter.dao

import com.akt.personalizer.dao.{AbstractDaoTest, CRUDDaoTest}
import com.akt.personalizer.datacenter.model.DataCenterModelBuilders.directoryStatus
import com.akt.personalizer.datacenter.model.DirectoryStatus
import org.scalatest.BeforeAndAfter
import org.scalatest.concurrent.ScalaFutures

/**
  * @author kostas.kougios
  *         13/06/18 - 11:37
  */
class DirectoryStatusDaoTest
    extends AbstractDaoTest
    with CRUDDaoTest[DirectoryStatus]
    with BeforeAndAfter
    with ScalaFutures {
  val dao = injector.instance[DirectoryStatusDao]

  override def createDomainModel = directoryStatus()

  after {
    import tables.profile.api._
    execute(tables.DirectoryStatus.delete)
  }
}
