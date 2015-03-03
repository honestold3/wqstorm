package storm.demo;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Values;
import backtype.storm.tuple.Fields;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import redis.clients.jedis.Jedis;

import java.util.Map;
import org.apache.log4j.Logger;


public class ClickSpout extends BaseRichSpout {

    public static Logger LOG = Logger.getLogger(ClickSpout.class);

    private Jedis jedis;
    private String host;
    private int port;
    private SpoutOutputCollector collector;


    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields(storm.demo.Fields.IP,
                storm.demo.Fields.URL,
                storm.demo.Fields.CLIENT_KEY));
    }

    @Override
    public void open(Map conf, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        host = conf.get(storm.demo.Conf.REDIS_HOST_KEY).toString();
        port = Integer.valueOf(conf.get(Conf.REDIS_PORT_KEY).toString());
        this.collector = spoutOutputCollector;
        connectToRedis();
    }

    private void connectToRedis() {
        jedis = new Jedis(host, port);
    }

    @Override
    public void nextTuple() {
        String content = jedis.rpop("count");
        if(content==null || "nil".equals(content)) {
            try { Thread.sleep(300); } catch (InterruptedException e) {}
        } else {
            JSONObject obj=(JSONObject) JSONValue.parse(content);
            String ip = obj.get(storm.demo.Fields.IP).toString();
            String url = obj.get(storm.demo.Fields.URL).toString();
            String clientKey = obj.get(storm.demo.Fields.CLIENT_KEY).toString();
            collector.emit(new Values(ip,url,clientKey));
        }
    }
}
