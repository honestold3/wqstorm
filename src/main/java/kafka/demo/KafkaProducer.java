package kafka.demo;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.Properties;

public class KafkaProducer {
	public static void main(String[] args) {
		Properties props = new Properties();
		//broker列表
		//props.put("metadata.broker.list", "192.168.1.170:9092,192.168.1.171:9092,192.168.1.172:9092");
        props.put("metadata.broker.list", "localhost:9092");
		//消息的序列化类
		props.put("serializer.class", "kafka.serializer.StringEncoder");
		props.put("zookeeper.connection.timeout.ms", 999999999);
		ProducerConfig config = new ProducerConfig(props);
		//key是topic，value是消息类型
		Producer<String, String> producer = new Producer<String, String>(config);
		
		//KeyedMessage<String, String> message = new KeyedMessage<String, String>("test", "hello you");
		for (int i = 0; i < 10; i++) {
			//构造向不同分区写入数据的生产者
			KeyedMessage<String, String> message = new KeyedMessage<String, String>("kankan", (i%2)+"", i+"");
			//把消息发送给broker
			producer.send(message);
		}
		producer.close();
	}
}
