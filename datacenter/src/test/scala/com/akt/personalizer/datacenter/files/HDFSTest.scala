package com.akt.personalizer.datacenter.files

import java.io.File

import org.scalatest.FunSuite
import org.scalatest.Matchers._

/**
  * @author kostas.kougios
  *         11/06/18 - 14:11
  */
class HDFSTest extends FunSuite
{
	val hdfs = HDFS()

	test("withOutputStream") {
		val f = new File("/tmp/withOutputStream.txt")
		f.delete()
		hdfs.withOutputStream(f.getAbsolutePath) {
			out =>
				out.writeUTF("hello\nworld\nπ")
		}

		val r = hdfs.withInputStream(f.getAbsolutePath) {
			in =>
				in.readUTF()
		}
		r should be("hello\nworld\nπ")
	}
}
