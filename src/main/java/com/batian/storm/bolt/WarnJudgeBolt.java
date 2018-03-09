package com.batian.storm.bolt;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import com.batian.storm.conf.ConfigurationManager;
import com.batian.storm.conf.Constants;
import com.batian.storm.tools.JsonUtil;
import com.batian.storm.tools.WarnOperation;
import com.batian.storm.tools.WeatherBean;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Ricky on 2018/3/8
 *
 * @author Administrator
 */
public class WarnJudgeBolt extends BaseRichBolt {
    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger( DataFilterBolt.class.getSimpleName() );

    private OutputCollector outputCollector;

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.outputCollector = collector;
    }

    @Override
    public void execute(Tuple input) {
        ArrayList<WeatherBean> weatherSeries;
        String cityKey = input.getString( 0 );
        String startTime = input.getString( 1 );
        String jsonData = input.getString( 3 );
        int cycle15 = ConfigurationManager.getInteger( Constants.QUERY_CYCLE_15 );
        int cycle10 = ConfigurationManager.getInteger( Constants.QUERY_CYCLE_10 );

        try {
            JsonUtil jsonUtil = new JsonUtil( jsonData );
            weatherSeries = jsonUtil.getWeatherSeries();
            if (weatherSeries.size() + 1 == cycle15) {
                outputCollector.emit( input, new Values( cityKey, startTime, jsonData ) );
                WarnOperation warnOperation = new WarnOperation( weatherSeries );
                if (warnOperation.isCold( cycle15 )) {
                    int tmpMax = warnOperation.getTmpMax( cycle15 );
                    int tmpMin = warnOperation.getTmpMin( cycle15 );
                    int tmpAvg = warnOperation.getTmpAvg( cycle15 );
                    outputCollector.emit( "blotA", new Values( cityKey, startTime, String.valueOf( cycle15 ), tmpAvg, tmpMin, tmpMax ) );
                }else if (warnOperation.isCold( cycle10 )) {
                    int tmpMax = warnOperation.getTmpMax( cycle10 );
                    int tmpMin = warnOperation.getTmpMin( cycle10 );
                    int tmpAvg = warnOperation.getTmpAvg( cycle10 );
                    outputCollector.emit( "blotB", new Values( cityKey, startTime, String.valueOf( cycle10 ), tmpAvg, tmpMin, tmpMax ) );
                }else {
                    logger.info( "未发现寒流天气!" );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declareStream( "blotA", new Fields( "Afield" ) );
        declarer.declareStream( "blotB",new Fields( "Bfield" ) );
    }
}
