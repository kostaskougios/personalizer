package com.akt.personalizer.rdd

import com.aktit.personalizer.model.{TableDef, ToNextVersion}
import org.apache.spark.rdd.RDD

import scala.reflect.ClassTag

/**
  * @author kostas.kougios
  *         10/06/18 - 17:57
  */

object PersonalizerRDDImplicits
{

	implicit class ConsumerRDD(rdd: RDD[ChannelInput])
	{

		def toTableRows[TABLE: ClassTag](tableDef: TableDef[TABLE]) = {
			rdd.map {
				case ChannelInput(time, data) =>
					val row = makeUpToDate[TABLE](tableDef.serdes.deserializeOneAnyVersion(data))
					ChannelOutput(time, row)
			}
		}

		private def makeUpToDate[TABLE](a: Any): TABLE = a match {
			case to: ToNextVersion[_] => makeUpToDate(to.toNextVersion)
			case t: TABLE@unchecked => t
		}
	}

}
