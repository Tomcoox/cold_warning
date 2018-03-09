package com.batian.storm.bolt;

import backtype.storm.tuple.Tuple;
import org.apache.storm.hbase.bolt.mapper.HBaseMapper;
import org.apache.storm.hbase.common.ColumnList;

/**
 * Created by Ricky on 2018/3/7
 *
 * @author Administrator
 */
public class HbaseMapperBolt implements HBaseMapper {
    @Override
    public byte[] rowKey(Tuple tuple) {
        return new byte[0];
    }

    @Override
    public ColumnList columns(Tuple tuple) {
        return null;
    }
}
