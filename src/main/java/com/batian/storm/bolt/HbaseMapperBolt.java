package com.batian.storm.bolt;

import backtype.storm.tuple.Tuple;
import com.batian.storm.conf.Constants;
import com.batian.storm.tools.JsonUtil;
import com.batian.storm.tools.WeatherBean;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.storm.hbase.bolt.mapper.HBaseMapper;
import org.apache.storm.hbase.common.ColumnList;

import java.util.ArrayList;

/**
 * Created by Ricky on 2018/3/7
 *
 * @author Administrator
 */
public class HbaseMapperBolt implements HBaseMapper {
    @Override
    public byte[] rowKey(Tuple tuple) {
        String cityKey = tuple.getString( 0 );
        String time = tuple.getString( 1 );
        String rowKey = cityKey + time;
        return rowKey.getBytes();
    }

    @Override
    public ColumnList columns(Tuple tuple) {
        System.out.println("存入HBASE");
        ArrayList<WeatherBean> weatherSeries;
        ColumnList cols = new ColumnList();
        String jsonData = tuple.getString( 2 );
        try {
            JsonUtil jsonUtil = new JsonUtil(jsonData);
            weatherSeries = jsonUtil.getWeatherSeries();
            for (int i = 0; i < weatherSeries.size(); i++) {
                String family = String.valueOf( i );
                WeatherBean arr = weatherSeries.get( i );
                cols.addColumn( family.getBytes(), Constants.TMP_MAX.getBytes(),Bytes.toBytes( arr.getTmp_max() ));
                cols.addColumn( family.getBytes(), Constants.TMP_MIN.getBytes(), Bytes.toBytes( arr.getTmp_min() ) );
                cols.addColumn( family.getBytes(), Constants.W_AM.getBytes(), Bytes.toBytes( arr.getW_am( ) ) );
                cols.addColumn( family.getBytes(), Constants.W_PM.getBytes(), Bytes.toBytes( arr.getW_pm()) );
                cols.addColumn( family.getBytes(), Constants.CW_AM.getBytes(), Bytes.toBytes( arr.getCw_am()) );
                cols.addColumn( family.getBytes(), Constants.CW_PM.getBytes(), Bytes.toBytes( arr.getCw_pm()) );
                cols.addColumn( family.getBytes(), Constants.CWD.getBytes(), Bytes.toBytes( arr.getCwd() ) );
                cols.addColumn( family.getBytes(), Constants.WD.getBytes(), Bytes.toBytes( arr.getWd() ) );
                cols.addColumn( family.getBytes(), Constants.WIND.getBytes(), Bytes.toBytes( arr.getWind() ) );
                cols.addColumn( family.getBytes(), Constants.SUNRISE.getBytes(), Bytes.toBytes( arr.getSunrise() ) );
                cols.addColumn( family.getBytes(), Constants.SUNSET.getBytes(), Bytes.toBytes( arr.getSunset() ) );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return cols;
    }
}
