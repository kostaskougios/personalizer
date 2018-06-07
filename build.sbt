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
		) ++ Spark.Core
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
)

lazy val collectorService = project.settings(commonSettings: _*).settings(
	libraryDependencies ++= {
		Seq(
			Libraries.ScalaTest,
			Libraries.Apache.Lang3,
			Libraries.Apache.CommonsIO,
			Kafka.Clients
		)
	}
).dependsOn(model % "test->test;compile->compile", avro % "test->test;compile->compile")

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

lazy val exampleSocialNetwork = project.settings(commonSettings: _*).settings(
	libraryDependencies ++= {
		Seq(
			Libraries.ScalaTest,
			Libraries.Apache.Lang3,
			Libraries.Apache.CommonsIO
		) ++ Spark.Core
	}
).dependsOn(common % "test->test;compile->compile", model % "test->test;compile->compile")
	.enablePlugins(PackPlugin)
