package com.aktit.avro

/**
  * @author kostas.kougios
  *         08/06/18 - 13:27
  */
case class VersionedSerializers[TABLE](
	previousVersionSerializers: Seq[AvroVersionedSerdes[_]],
	currentVersionSerializer: AvroVersionedSerdes[TABLE]
)