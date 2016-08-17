package storm.drpc;

import java.util.Map;

import backtype.storm.Config;
import backtype.storm.StormSubmitter;
import backtype.storm.drpc.LinearDRPCTopologyBuilder;
import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class DrpcRemoteTopology {
	/**
	 *
	 *
	 */
	public static class WqBolt extends BaseRichBolt{
		private OutputCollector collector;
		public void prepare(Map stormConf, TopologyContext context,
				OutputCollector collector) {
			this.collector = collector;
		}


		public void execute(Tuple input) {
			String value = input.getString(1);
			String result = "hello " + value;
			collector.emit(new Values(input.getValue(0), result));
		}

		public void declareOutputFields(OutputFieldsDeclarer declarer) {

            declarer.declare(new Fields("id", "result"));
		}
		
	}
	
	//storm jar jar.jar  xxxx
	public static void main(String[] args) throws Exception{
		LinearDRPCTopologyBuilder builder = new LinearDRPCTopologyBuilder("hello");
		builder.addBolt(new WqBolt());
		
		Config config = new Config();
		

		StormSubmitter.submitTopology("testDrpc", config, builder.createRemoteTopology());
	}
}
