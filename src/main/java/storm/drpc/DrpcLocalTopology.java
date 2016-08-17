package storm.drpc;

import java.util.Map;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.LocalDRPC;
import backtype.storm.StormSubmitter;
import backtype.storm.drpc.LinearDRPCTopologyBuilder;
import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class DrpcLocalTopology {

	public static class MyBolt extends BaseRichBolt{
		private OutputCollector collector;
		public void prepare(Map stormConf, TopologyContext context,
				OutputCollector collector) {
			this.collector = collector;
		}


		public void execute(Tuple input) {
			String value = input.getString(1);
			String result = "hello 2342342 " + value;
			collector.emit(new Values(input.getValue(0), result));
		}

		public void declareOutputFields(OutputFieldsDeclarer declarer) {
			declarer.declare(new Fields("id", "result"));
		}
		
	}
	
	//storm jar jar.jar  xxxx
	public static void main(String[] args) throws Exception{
		LinearDRPCTopologyBuilder builder = new LinearDRPCTopologyBuilder("hello");
		builder.addBolt(new MyBolt());
		
		Config config = new Config();
		
		//本地运行
		LocalCluster localCluster = new LocalCluster();
		LocalDRPC drpcServer = new LocalDRPC();
		localCluster.submitTopology(DrpcLocalTopology.class.getSimpleName(), config, builder.createLocalTopology(drpcServer));
		
		//客户端调用
		String result = drpcServer.execute("hello", "world");
		System.err.println("result:"+result);
		
		
		localCluster.shutdown();
		drpcServer.shutdown();

		
	
	}
}
