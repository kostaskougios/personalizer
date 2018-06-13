package com.akt.personalizer.datacenter.rdd

/**
  * @author kostas.kougios
  *         10/06/18 - 20:16
  */
case class ChannelOutput[TABLE](time: Long, row: TABLE)