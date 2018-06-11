package com.akt.personalizer.files

import java.net.URI

import org.apache.commons.io.IOUtils
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs._

/**
  * @author kostas.kougios
  *         11/06/18 - 14:04
  */
class HDFS private(filesystem: FileSystem)
{
	def merge(srcPath: String, dstPath: String) = {
		FileUtil.copyMerge(filesystem, new Path(srcPath), filesystem, new Path(dstPath), false, new Configuration(), null)
	}

	def withInputStream[R](path: String)(processor: FSDataInputStream => R): R = {
		val in = inputStream(path)
		try {
			processor(in)
		} finally {
			in.close()
		}
	}

	def inputStream(path: String): FSDataInputStream = filesystem.open(new Path(path))

	def write(file: String, value: String): Unit = withOutputStream(file) {
		out =>
			IOUtils.write(value, out, "UTF-8")
	}

	def withOutputStream(path: String)(processor: FSDataOutputStream => Unit): Unit = {
		val out = outputStream(path)
		try {
			processor(out)
		} finally {
			out.close()
		}
	}

	def outputStream(path: String): FSDataOutputStream = filesystem.create(new Path(path))

	def write(file: String, value: Array[Byte]): Unit = withOutputStream(file) {
		out =>
			IOUtils.write(value, out)
	}

	def exists(path: String) = filesystem.exists(new Path(path))

	def delete(path: String, recursive: Boolean = true) = {
		if (path.trim == "/") throw new IllegalArgumentException("will not delete /")
		filesystem.delete(new Path(path), recursive)
	}

	def listStatus(srcPath: String, recursive: Boolean = false): Iterator[FileStatus] = listStatus(new Path(srcPath), recursive)

	def listStatus(srcPath: Path, recursive: Boolean): Iterator[FileStatus] = {
		val statuses = filesystem.listStatus(srcPath).toStream
		val s = if (recursive) {
			statuses #::: statuses.filter(fs => fs.isDirectory && fs.getPath != srcPath).flatMap(dir => listStatus(dir.getPath, recursive))
		} else statuses
		s.toIterator
	}

	def listDirectories(srcDir: String): Iterator[FileStatus] = listStatus(srcDir).filter(_.isDirectory)
}

object HDFS
{
	private val Config = new Configuration(false)

	def apply() = new HDFS(FileSystem.get(Config))

	def apply(defaultFS: String) = new HDFS(FileSystem.get(new URI(defaultFS), Config))
}