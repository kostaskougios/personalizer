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
		val postProducer = Producer(channelFactory.channel[Post], PostSerializers)

		for (i <- 1 to 1000) {
			postProducer.produce()
			new Post(5, "hello world", Some("Hello world content"), None)
		}

		Thread.sleep(1000)
	} finally {
		channelFactory.close()
	}
}
