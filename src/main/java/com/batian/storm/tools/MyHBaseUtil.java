package com.batian.storm.tools;

import com.batian.storm.conf.ConfigurationManager;
import com.batian.storm.conf.Constants;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HConnectionManager;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.log4j.Logger;
import java.io.IOException;

/**
 * Created by Ricky on 2018/3/7
 *
 * @author Administrator
 */
public class MyHBaseUtil {
    private static final Logger LOGGER = Logger.getLogger( MyHBaseUtil.class.getSimpleName() );
    public static Configuration configuration;
    private static HConnection conn = null;

    static {
        configuration = HBaseConfiguration.create();
        configuration.set( "hbase.zookeeper.property.clientPort", ConfigurationManager.getProperty( Constants.ZK_PORT ) );
        configuration.set( "hbase.zookeeper.property.quorum", ConfigurationManager.getProperty( Constants.ZK_LIST ) );
        try {
            conn = HConnectionManager.createConnection( configuration );
        } catch (IOException e) {
            LOGGER.error( "创建HBASE连接异常！" + e.getMessage());
        }
    }

    public void saveData(String rowkey,String family,String qualifier, String value) {
        HTableInterface hTable = null;
        try {
            hTable = conn.getTable( ConfigurationManager.getProperty( Constants.HBASE_TABLE ) );
            Put put = new Put( rowkey.getBytes() );
            put.add( family.getBytes(), qualifier.getBytes(), value.getBytes() );
            hTable.put( put );
            LOGGER.info( "存储天气数据成功！" );
        } catch (IOException e) {
            LOGGER.error( "存储天气数据失败：" + e.getMessage() );
        }

    }
}
