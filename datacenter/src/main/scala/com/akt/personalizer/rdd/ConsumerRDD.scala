package com.akt.personalizer.rdd

import com.aktit.personalizer.model.{TableDef, ToNextVersion}
import org.apache.spark.rdd.RDD

import scala.reflect.ClassTag

/**
  * @author kostas.kougios
  *         10/06/18 - 17:57
  */
class ConsumerRDD(rdd: RDD[(Long, Array[Byte])])
{

	def toTableRows[TABLE: ClassTag](tableDef: TableDef[TABLE]) = {
		rdd.map {
			case (time, data) =>
				val row = makeUpToDate[TABLE](tableDef.serdes.deserializeOneAnyVersion(data))
				(time, row)
		}
	}

	private def makeUpToDate[TABLE](a: Any): TABLE = a match {
		case to: ToNextVersion[_] => makeUpToDate(to.toNextVersion)
		case t: TABLE@unchecked => t
	}
}
