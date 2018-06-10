package com.aktit.avro

import scala.reflect.{ClassTag, classTag}

/**
  * @author kostas.kougios
  *         08/06/18 - 13:27
  */
case class VersionedSerializers[TABLE: ClassTag](
	previousVersionSerializers: Seq[AvroVersionedSerdes[_]],
	currentVersionSerializer: AvroVersionedSerdes[TABLE]
)
{
	private val tableDef = classTag[TABLE].runtimeClass.getSimpleName
	private val prev = previousVersionSerializers.groupBy(_.version).mapValues(_.head)

	def deserializeOneAnyVersion(data: Array[Byte]): Any = {
		val version = data(0)
		if (version == currentVersionSerializer.version)
			currentVersionSerializer.deserializeOne(data)
		else prev.getOrElse(version, throw new IllegalArgumentException(s"Unknown version $version for TableDef $tableDef"))
	}
}