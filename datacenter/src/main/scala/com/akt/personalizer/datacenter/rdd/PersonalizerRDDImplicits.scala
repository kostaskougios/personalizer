package com.akt.personalizer.datacenter.rdd

import com.akt.personalizer.datacenter.dao.DirectoryStatusDao
import com.akt.personalizer.datacenter.files.Directories
import com.akt.personalizer.datacenter.model.DirectoryStatus
import com.aktit.personalizer.di.GuiceFactory
import com.aktit.personalizer.model.TableDef
import com.aktit.personalizer.model.time.TimeSplitter
import com.aktit.personalizer.utils.FutureUtils.FutureEx
import com.aktit.utils.TimeMeasure
import org.apache.hadoop.io.{BytesWritable, LongWritable}
import org.apache.spark.SparkContext
import org.apache.spark.api.java.StorageLevels
import org.apache.spark.internal.Logging
import org.apache.spark.rdd.RDD

/**
  * @author kostas.kougios
  *         10/06/18 - 17:57
  */

object PersonalizerRDDImplicits extends Logging
{

	implicit class ConsumerRDD(rdd: RDD[ChannelInput])
	{
		def saveAsDataCenterFile[TABLE](driverGuice: GuiceFactory, dataDir: String, tableDef: TableDef[TABLE], timeSplitter: TimeSplitter): Unit = {
			val timeMeasure = TimeMeasure()

			timeMeasure.dt("saveAsDataCenterFile") {
				val persisted = rdd.persist(StorageLevels.MEMORY_AND_DISK)
				val timesSet = timeMeasure.dt("collect times") {
					persisted.map {
						ci =>
							timeSplitter.divideTime(ci.time)
					}.mapPartitions(_.toSet.iterator).collect().toSet
				}

				for (time <- timesSet) {
					val dt = timeSplitter.toRoundedUTCDateTime(time)
					val path = Directories.incomingDataDirectory(dataDir, tableDef, dt)

					val directoryStatusDao = driverGuice.injector.instance[DirectoryStatusDao]
					directoryStatusDao.create(DirectoryStatus(1, path, DirectoryStatus.UnderCreation)).await

					timeMeasure.dt(s"Save $path") {
						persisted.filter(ci => timeSplitter.divideTime(ci.time) == time)
							.map {
								ci =>
									(new LongWritable(ci.time), new BytesWritable(ci.data))
							}.saveAsSequenceFile(path)
					}
				}
			}

			logInfo(s"Times for saveAsDataCenterFile for ${tableDef.name} :\n$timeMeasure")
		}
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
