package com.mysn

import java.util.Date

import com.aktit.personalizer.channels.kafka.KafkaChannel
import com.aktit.personalizer.model.time.UTCDateTime
import com.aktit.personalizer.producers.Producer
import com.mysn.personalizer.tables._

/**
  * See bin/reset-simulation
  *
  * Get kafka group details (current offset etc) :
  *
  * kafka-consumer-groups.sh --bootstrap-server server.lan:9092 --describe --group social.PostGroup
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
	val SimulateMessagesPerMillis = 10
	val channelFactory = KafkaChannel.factory(Seq("server.lan:9092"))
	try {
		val producerFactory = Producer.factory(channelFactory)
		val postProducer = producerFactory.producer(Post)
		val viewProducer = producerFactory.producer(View)

		var time = System.currentTimeMillis
		var numOfMsgs = 0.toLong

		while (true) {
			for (_ <- 1 to SimulateMessagesPerMillis) {
				postProducer.produce(time, Post.row(time, s"hello world $time", Some(s"Hello world content $time"), None))
				viewProducer.produce(time, View.row(UTCDateTime.now, s"http://my.social/view/$time", s"http://referer$time"))
				numOfMsgs += 1
			}
			time += 1
			if (time % 1000 == 0) Thread.sleep(999)

			if (time % 10000 == 0) println(s"Simulation time is ${new Date(time)} , msgs : $numOfMsgs, wall clock time is ${new Date}")
		}
	} finally {
		channelFactory.close()
	}
}
