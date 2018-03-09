package com.batian.storm.bolt;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;
import org.apache.log4j.Logger;

import java.util.Map;

/**
 * Created by Ricky on 2018/3/8
 *
 * @author Administrator
 */
public class SendMailBolt extends BaseRichBolt {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger( DataFilterBolt.class.getSimpleName() );

    private OutputCollector outputCollector;

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.outputCollector = collector;
    }

    @Override
    public void execute(Tuple input) {
        String cityKey = input.getString( 0 );
        String startTime = input.getString( 1 );
        String reason = input.getString( 2 );

        String messge = cityKey + ":" + startTime + "---->" + reason;



    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }
}
