package storm.practice;

import java.util.Map;
import java.util.Random;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;


public class ClusterStormTopologyFieldGrouping {
	
	public static class DataSourceSpout extends BaseRichSpout{
		private Map conf;
		private TopologyContext context;
		private SpoutOutputCollector collector;
		
		final Random random = new Random();
		/**
		 * 首先被调用
		 */
		public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
			this.conf = conf;
			this.context = context;
			this.collector = collector;
		}
		
		/**
		 * 死循环的调用。线程安全的操作。
		 */
		int i = 0;
		public void nextTuple() {
			System.err.println("Spout  "+ i);
			//送出去，送个bolt
			//Values是一个value的List
			this.collector.emit(new Values(i%2 ,i++));
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


		public void declareOutputFields(OutputFieldsDeclarer declarer) {
			//Fields是一个field的List
			declarer.declare(new Fields("flag", "v1"));
		}
	}
	
	public static class SumBolt extends BaseRichBolt{
		private Map conf;
		private TopologyContext context;
		private OutputCollector collector;
		
		public void prepare(Map conf, TopologyContext context, OutputCollector collector) {
			this.conf = conf;
			this.context = context;
			this.collector = collector;
		}
		
		/**
		 * 死循环，用于接收bolt送来的数据
		 */
		int sum = 0;
		public void execute(Tuple tuple) {
			final Integer value = tuple.getIntegerByField("v1");
			sum += value;
			System.err.println(Thread.currentThread().getId() + "\t" +  value);
		}


		public void declareOutputFields(OutputFieldsDeclarer arg0) {
			// TODO Auto-generated method stub
			
		}

	}
	
    public static void main( String[] args ) throws AlreadyAliveException, InvalidTopologyException {
        final TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("input", new DataSourceSpout());
        //相同分组的tuple会分到同一个executor中
        //不同分组的tuple会不会分到同一个executor中哪？
        builder.setBolt("sum", new SumBolt(), 3).fieldsGrouping("input", new Fields("flag"));
        
        final Config config = new Config();
		StormSubmitter.submitTopology(ClusterStormTopologyFieldGrouping.class.getSimpleName(), config, builder.createTopology());
    }
}
