package com.mysn.personalizer.tables.v1

import com.aktit.personalizer.model.time.UTCDateTime

/**
  * @author kostas.kougios
  *         08/06/18 - 14:48
  */
case class View private[tables](
	time: UTCDateTime,
	url: String,
	referrerUrl: String
)