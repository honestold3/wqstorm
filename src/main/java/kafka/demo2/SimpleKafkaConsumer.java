package kafka.demo2;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by wq on 15/2/24.
 */
public class SimpleKafkaConsumer {

    public static void main(String[] args) {
        Properties props = new Properties();
        //props.put("zookeeper.connect","192.168.1.170:2181");
        props.put("zookeeper.connect","localhost:2181");
        props.put("group.id","aaaa");
        ConsumerConfig consumerConfig = new ConsumerConfig(props);
        //create connector
        ConsumerConnector consumer = Consumer.createJavaConsumerConnector(consumerConfig);
        //create message streaming
        Map<String,Integer> configMap = new HashMap<String,Integer>();
        configMap.put("kankan",3);
        Map<String,List<KafkaStream<byte[],byte[]>>> ms = consumer.createMessageStreams(configMap);
        KafkaStream<byte[],byte[]> stream = ms.get("kankan").get(0);
        ConsumerIterator<byte[],byte[]> it = stream.iterator();
        while (it.hasNext()){
            System.out.println("receive:"+new String(it.next().message()));
        }
    }
}

