import sbt._

object Deps {
  val ScalaVersion = "2.11.12" // see mock-spark if you change scala's major version

  object Scala {
    val Reflect = "org.scala-lang" % "scala-reflect" % ScalaVersion
    val Xml = Seq(
      "org.scala-lang.modules" %% "scala-xml" % "1.3.0",
      "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.10.3"
    )
  }

  object Spark {
    val Version = "2.4.5"
    private val SparkCore = "org.apache.spark" %% "spark-core" % Version
    val Core = Seq(
      SparkCore,
      "org.apache.spark" %% "spark-yarn" % Version, // we need this to deploy to yarn
      "ch.qos.logback" % "logback-classic" % "1.2.3"
    )
    val Streaming = "org.apache.spark" %% "spark-streaming" % Version
    val GraphX = "org.apache.spark" %% "spark-graphx" % Version
    val Sql = "org.apache.spark" %% "spark-sql" % Version

    val AllContainedInSparkSubmit = Seq(SparkCore, Streaming, GraphX, Sql)
  }

  object Kafka {
    val SparkStreaming = "org.apache.spark" %% "spark-streaming-kafka-0-10" % Spark.Version exclude ("net.jpountz.lz4", "lz4")
    val Clients = "org.apache.kafka" % "kafka-clients" % "2.4.1"
  }

  object Libraries {
    val Config = "com.typesafe" % "config" % "1.4.0"
    val ScalaTest = "org.scalatest" %% "scalatest" % "3.1.1" % "test"
    val Avro4S = "com.sksamuel.avro4s" %% "avro4s-core" % "1.8.4"

    object Apache {
      val CommonsDBCP2 = "org.apache.commons" % "commons-dbcp2" % "2.1"
      val Lang3 = "org.apache.commons" % "commons-lang3" % "3.7"
      val CommonsIO = "commons-io" % "commons-io" % "2.5"
      val Pool2 = "org.apache.commons" % "commons-pool2" % "2.5.0"
    }

  }

  object Slick {
    val Version = "3.3.2"

    val Slick = "com.typesafe.slick" %% "slick" % Version
    val CodeGen = "com.typesafe.slick" %% "slick-codegen" % Version
  }

  object PostGreSql {
    val Driver = "org.postgresql" % "postgresql" % "42.2.11.jre7"
  }

  object Database {
    val H2 = "com.h2database" % "h2" % "1.4.200"
    val H2Test = H2 % "test"
    val C3P0 = "com.mchange" % "c3p0" % "0.9.5.5"
  }

  object Inject {
    val Guice = "com.google.inject" % "guice" % "4.2.3"
    val ScalaGuice = "net.codingwell" %% "scala-guice" % "4.2.6"
  }

}
