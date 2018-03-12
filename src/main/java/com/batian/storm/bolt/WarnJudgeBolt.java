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
        String jsonData = input.getString( 2 );
        String id = cityKey + startTime;
        int cycle15 = ConfigurationManager.getInteger( Constants.QUERY_CYCLE_15 );
        int cycle10 = ConfigurationManager.getInteger( Constants.QUERY_CYCLE_10 );



        try {
            JsonUtil jsonUtil = new JsonUtil( jsonData );
            weatherSeries = jsonUtil.getWeatherSeries();
            if (weatherSeries.size() == cycle15) {
                outputCollector.emit( "save-hbase",input, new Values( cityKey, startTime, jsonData ) );
                WarnOperation warnOperation = new WarnOperation( weatherSeries );
                if (warnOperation.isCold( cycle15 )) {
                    int tmpMax = warnOperation.getTmpMax( cycle15 );
                    int tmpMin = warnOperation.getTmpMin( cycle15 );
                    int tmpAvg = warnOperation.getTmpAvg( cycle15 );
                    outputCollector.emit( "save-mysql-15",input, new Values(id, cityKey, startTime, cycle15, tmpAvg, tmpMin, tmpMax ) );
                }else {
                    logger.info( cityKey + ":15天预警未发现寒流天气!" );
                }
                if (warnOperation.isCold( cycle10 )) {
                    int tmpMax = warnOperation.getTmpMax( cycle10 );
                    int tmpMin = warnOperation.getTmpMin( cycle10 );
                    int tmpAvg = warnOperation.getTmpAvg( cycle10 );
                    outputCollector.emit( "save-mysql-10",input, new Values( id, cityKey,startTime, cycle10 , tmpAvg, tmpMin, tmpMax ) );
                }else {
                    logger.info( cityKey + ":10天预警未发现寒流天气!" );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declareStream( "save-hbase", new Fields( "cityKey","startTime","jsonData" ) );
        declarer.declareStream( "save-mysql-15",new Fields( "id","city_key","start_time","cycle","tmp_avg","tmp_min","tmp_max" ) );
        declarer.declareStream( "save-mysql-10",new Fields( "id","city_key","start_time","cycle","tmp_avg","tmp_min","tmp_max" ) );
    }
}
