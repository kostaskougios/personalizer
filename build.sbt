import Deps._

name := "personalizer"

version in ThisBuild := "1.0"

scalaVersion in ThisBuild := Deps.ScalaVersion

javacOptions in ThisBuild ++= Seq("-source", "1.8", "-target", "1.8")

scalacOptions in ThisBuild ++= Seq("-target:jvm-1.7", "-unchecked", "-feature", "-deprecation")

resolvers in ThisBuild += Resolver.mavenLocal

val commonSettings = Seq(
	version := "1.0",
	excludeDependencies ++= Seq(
		// commons-logging is replaced by jcl-over-slf4j
		ExclusionRule("org.slf4j", "slf4j-log4j12")
	)
)

lazy val common = project.settings(commonSettings: _*).settings(
	libraryDependencies ++= {
		Seq(
			Libraries.ScalaTest,
			Libraries.Apache.Lang3,
			Libraries.Apache.CommonsIO
		)
	}
)

lazy val avro = project.settings(commonSettings: _*).settings(
	libraryDependencies ++= {
		Seq(
			Libraries.ScalaTest,
			Libraries.Apache.Lang3,
			Libraries.Apache.CommonsIO,
			Libraries.Avro4S
		)
	}
)

lazy val model = project.settings(commonSettings: _*).settings(
	libraryDependencies ++= {
		Seq(
			Libraries.ScalaTest,
			Libraries.Apache.Lang3,
			Libraries.Apache.CommonsIO
		)
	}
).dependsOn(avro % "test->test;compile->compile")

lazy val producers = project.settings(commonSettings: _*).settings(
	libraryDependencies ++= {
		Seq(
			Libraries.ScalaTest,
			Libraries.Apache.Lang3,
			Libraries.Apache.CommonsIO
		)
	}
).dependsOn(model % "test->test;compile->compile", avro % "test->test;compile->compile")

lazy val kafkaProducers = project.settings(commonSettings: _*).settings(
	libraryDependencies ++= {
		Seq(
			Libraries.ScalaTest,
			Libraries.Apache.Lang3,
			Libraries.Apache.CommonsIO,
			Kafka.Clients
		)
	}
).dependsOn(producers % "test->test;compile->compile")

lazy val kafkaConsumers = project.settings(commonSettings: _*).settings(
	libraryDependencies ++= {
		Seq(
			Libraries.ScalaTest,
			Libraries.Apache.Lang3,
			Libraries.Apache.CommonsIO,
			Kafka.SparkStreaming
		)
	}
).dependsOn(datacenter)

lazy val experiments = project.settings(commonSettings: _*).settings(
	libraryDependencies ++= {
		Seq(
			Libraries.ScalaTest,
			Spark.Sql,
			Libraries.Apache.Lang3,
			Libraries.Apache.CommonsIO
		) ++ Spark.Core
	}
).dependsOn(common % "test->test;compile->compile")
	.enablePlugins(PackPlugin)

lazy val slick = TaskKey[Seq[File]]("gen-tables")

lazy val tables = project.settings(
	commonSettings,
	scalacOptions -= "-unchecked",
	slick := {
		val dir = sourceDirectory.value
		val cp = (dependencyClasspath in Compile).value
		val r = (runner in Compile).value
		val s = streams.value
		val outputDir = (dir / "main" / "scala").getPath
		val url = "jdbc:postgresql://server.lan/personalizer"
		val username = "personalizer"
		val password = "123"
		val jdbcDriver = "org.postgresql.Driver"
		val slickDriver = "slick.jdbc.PostgresProfile"
		val pkg = "com.aktit.personalizer.dao.slickgenerated"
		r.run("slick.codegen.SourceCodeGenerator", cp.files, Array(slickDriver, jdbcDriver, url, outputDir, pkg, username, password), s.log)
		val fName = outputDir + pkg.replace('.', '/') + "/Tables.scala"
		Seq(file(fName))
	},
	libraryDependencies ++= {
		import Deps._
		Seq(
			Slick.Slick,
			Slick.CodeGen,
			PostGreSql.Driver
		)
	}
)

lazy val dao = project.settings(commonSettings: _*).settings(
	libraryDependencies ++= {
		Seq(
			Libraries.ScalaTest,
			Libraries.Apache.Lang3,
			Libraries.Apache.CommonsIO,
			Slick.Slick,
			Database.H2Test,
			Database.C3P0,
			Inject.Guice
		)
	}
).dependsOn(common, model, tables)

lazy val datacenter = project.settings(commonSettings: _*).settings(
	libraryDependencies ++= {
		Seq(
			Libraries.ScalaTest,
			Spark.Streaming,
			Libraries.Apache.Lang3,
			Libraries.Apache.CommonsIO,
			Spark.Sql,
			Slick.Slick,
			Database.H2Test,
			Database.C3P0
		) ++ Spark.Core
	}
).dependsOn(common % "test->test;compile->compile", model % "test->test;compile->compile", avro, dao % "test->test;compile->compile")

lazy val exampleSocialNetwork = project.settings(commonSettings: _*).settings(
	libraryDependencies ++= {
		Seq(
			Libraries.ScalaTest,
			Libraries.Apache.Lang3,
			Libraries.Apache.CommonsIO
		) ++ Spark.Core
	}
).dependsOn(producers, kafkaProducers)

lazy val exampleSocialNetworkDatacenter = project.settings(commonSettings: _*).settings(
	libraryDependencies ++= {
		Seq(
			Libraries.ScalaTest,
			Libraries.Apache.Lang3,
			Libraries.Apache.CommonsIO,
			PostGreSql.Driver
		) ++ Spark.Core
	}
).dependsOn(producers, datacenter, kafkaConsumers, exampleSocialNetwork)
	.enablePlugins(PackPlugin)
