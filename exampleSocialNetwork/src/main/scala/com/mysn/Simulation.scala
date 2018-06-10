package com.mysn

import com.aktit.personalizer.channels.kafka.KafkaChannel
import com.aktit.personalizer.model.time.UTCDateTime
import com.aktit.personalizer.producers.Producer
import com.mysn.personalizer.tables._

/**
  * Create topics, i.e.:
  *
  * kafka-topics.sh --create --zookeeper server.lan:2181 --replication-factor 1 --partitions 16 --topic social.Post
  *
  * Delete topics:
  *
  * kafka-topics.sh --zookeeper server.lan:2181 --delete --topic social.Post
  *
  * Get kafka group details (current offset etc) :
  *
  * kafka-consumer-groups.sh --bootstrap-server server.lan:9092 --describe --group social.Post
  *
  * Run it:
  * bin/example-social-network-consumer
  *
  * Then view incoming data at
  *
  * hdfs dfs -ls /social-network/incoming/social.Post
  *
  * @author kostas.kougios
  *         08/06/18 - 00:06
  */
object Simulation extends App
{
	val channelFactory = KafkaChannel.factory(Seq("server.lan:9092"))
	try {
		val producerFactory = Producer.factory(channelFactory)
		val postProducer = producerFactory.producer(Post)
		val viewProducer = producerFactory.producer(View)

		for (i <- 1 to 1000000) {
			val time = System.currentTimeMillis
			postProducer.produce(time, Post.row(i, s"hello world $i", Some(s"Hello world content $i"), None))
			viewProducer.produce(time, View.row(UTCDateTime.now, s"http://my.social/view/$i", s"http://referer$i"))
		}

		Thread.sleep(1000)
	} finally {
		channelFactory.close()
	}
}
