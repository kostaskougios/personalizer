package com.mysn

import com.aktit.personalizer.channels.kafka.KafkaChannel
import com.aktit.personalizer.producers.Producer
import com.mysn.personalizer.tables._

/**
  * @author kostas.kougios
  *         08/06/18 - 00:06
  */
object Simulation extends App
{
	val channelFactory = KafkaChannel.factory(Seq("server.lan:9092"))
	try {
		val producerFactory = Producer.factory(channelFactory)
		val postProducer = producerFactory.producer(Post)

		for (i <- 1 to 1000) {
			val row = Post.row(i, s"hello world $i", Some(s"Hello world content $i"), None)
			postProducer.produce(row)
		}

		Thread.sleep(1000)
	} finally {
		channelFactory.close()
	}
}
