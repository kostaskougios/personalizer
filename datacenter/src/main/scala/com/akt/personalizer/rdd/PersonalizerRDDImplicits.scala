package com.akt.personalizer.rdd

import com.akt.personalizer.files.Directories
import com.aktit.personalizer.model.TableDef
import org.apache.hadoop.io.{BytesWritable, LongWritable}
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD

/**
  * @author kostas.kougios
  *         10/06/18 - 17:57
  */

object PersonalizerRDDImplicits
{

	implicit class ConsumerRDD(rdd: RDD[ChannelInput])
	{
		def saveAsDataCenterFile[TABLE](dataDir: String, tableDef: TableDef[TABLE]): Unit =
			saveAsDataCenterFile(Directories.incomingDataNowDirectory(dataDir, tableDef))

		def saveAsDataCenterFile(path: String): Unit = rdd.map {
			ci =>
				(new LongWritable(ci.time), new BytesWritable(ci.data))
		}.saveAsSequenceFile(path)
	}

	implicit class DataCenterOps(sc: SparkContext)
	{
		def dataCenterFile(path: String) = sc.sequenceFile(path, classOf[LongWritable], classOf[BytesWritable])
			.map {
				case (lw, bw) =>
					ChannelInput(lw.get, bw.getBytes)
			}
	}
}
