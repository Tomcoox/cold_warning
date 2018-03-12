import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;
import org.junit.Test;

import java.io.File;
import java.util.Map;

/**
 * Created by Ricky on 2018/3/10
 *
 * @author Administrator
 */
public class Spout extends BaseRichSpout {
    private SpoutOutputCollector collector;
    String data = "101030500{\"resultcode\":\"112\",\"reason\":\"当前可请求的次数不足\",\"result\":null,\"error_code\":10012}";
    @Override
    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        this.collector = collector;
    }

    @Override
    public void nextTuple() {
        // File file = new File( "data/data" );
        this.collector.emit(new Values(data));
        Utils.sleep(10000);

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare( new Fields( "data" ) );
    }

// @Test
// public  void test() {
//     File file = new File( "data/data" );
//     System.out.println(    file.toString() );
// }
}