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
import com.batian.storm.tools.WeatherBean;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Ricky on 2018/3/7
 *
 * @author Administrator
 */
public class DataFilterBolt extends BaseRichBolt {
    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger( DataFilterBolt.class.getSimpleName() );

    private OutputCollector outputCollector;

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.outputCollector = collector;
    }

    @Override
    public void execute(Tuple input) {
        String datas = input.getString( 0 );
        String cityKey = datas.substring( 0, 9 );
        String jsonData = datas.substring( 9 );
        int errorCode;
        String reason;
        String startTime;

        try {
            JsonUtil jsonUtil = new JsonUtil( jsonData );
            errorCode = jsonUtil.getErrorCode();
            reason = jsonUtil.getReason();
            startTime = jsonUtil.getStartTime();
            if (errorCode == ConfigurationManager.getInteger( Constants.SUCCEED_CODE )) {
                outputCollector.emit( input, new Values( cityKey, startTime,reason, jsonData ) );
            } else {
                outputCollector.emit( input, new Values( cityKey, startTime, reason ) );
                logger.error( "城市天气数据查询失败：" + reason +"!将发送邮件通知开发人员！");
            }
        } catch (Exception e) {
            logger.error( "JSON数据格式不符合:" + e.getMessage() );
        }


    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare( new Fields( "cityKey","startTime","reason","jsonData" ) );
    }
}
