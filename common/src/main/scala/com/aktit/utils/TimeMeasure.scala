package com.aktit.utils

/**
  * @author kostas.kougios
  *         26/05/18 - 23:45
  */
class TimeMeasure()
{
	private val b = Seq.newBuilder[(String, Long)]

	def dt[R](name: String)(f: => R): R = {
		val (t, r) = TimeMeasure.dt(f)
		b += ((name, t))
		r
	}

	override def toString = b.result.map {
		case (name, time) => s"$name : $time ms"
	}.mkString("\n")
}

object TimeMeasure
{
	def apply() = new TimeMeasure()

	/**
	  * Measures how long f takes
	  *
	  * @param f the function to measure
	  * @tparam R the return type of f
	  * @return (dt : time in millis, R as returned by f)
	  */
	def dt[R](f: => R): (Long, R) = {
		val start = System.currentTimeMillis
		val r = f
		val dt = System.currentTimeMillis - start
		(dt, r)
	}
}
