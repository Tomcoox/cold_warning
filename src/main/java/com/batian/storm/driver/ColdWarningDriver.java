package com.batian.storm.driver;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.spout.SchemeAsMultiScheme;
import backtype.storm.topology.TopologyBuilder;
import com.batian.storm.bolt.*;
import com.batian.storm.conf.ConfigurationManager;
import com.batian.storm.conf.Constants;
import org.apache.storm.hbase.bolt.HBaseBolt;
import org.apache.storm.jdbc.bolt.JdbcInsertBolt;
import storm.kafka.*;


import java.util.Collections;
import java.util.HashMap;

/**
 * Created by Ricky on 2018/3/7
 *
 * @author Administrator
 */
public class ColdWarningDriver {

    public static void main(String[] args) throws AlreadyAliveException, InvalidTopologyException {

        //storm conf
        Config stormConf = new Config();

        //hbase conf
        HashMap<String, String> hbaseConf = new HashMap<String, String>();
        hbaseConf.put( "hbase.rootdir", ConfigurationManager.getProperty( Constants.ROOT_DIR ) );
        stormConf.put( "hbase.conf", hbaseConf );
        //hbase bolt
        HbaseMapperBolt hbaseMapperBolt = new HbaseMapperBolt();
        HBaseBolt hbaseBolt = new HBaseBolt( ConfigurationManager.getProperty( Constants.HBASE_TABLE ),
                hbaseMapperBolt).withConfigKey( "hbase.conf" );

        //jdbc bolt
        JdbcInsertBolt jdbcInsert15Bolt = new JdbcMapper15Bolt().getJdbcInsertBolt();
        JdbcInsertBolt jdbcInsert10Bolt = new JdbcMapper10Bolt().getJdbcInsertBolt();

        //kafka conf
        BrokerHosts brokerHosts = new ZkHosts( ConfigurationManager.getProperty( Constants.ZK_SERVER ) );

        //Spout conf
        SpoutConfig spoutConfig = new SpoutConfig( brokerHosts, ConfigurationManager.getProperty( Constants.KAFKA_TOPIC ),
                ConfigurationManager.getProperty( Constants.ZK_ROOT ), ConfigurationManager.getProperty( Constants.ZK_ID ) );
        //spout model
        spoutConfig.scheme =new  SchemeAsMultiScheme( new StringScheme() );
        spoutConfig.forceFromStart = false;
        //zk
        spoutConfig.zkServers = Collections.singletonList( ConfigurationManager.getProperty( Constants.ZK_LIST ) );
        spoutConfig.zkPort = ConfigurationManager.getInteger( Constants.ZK_PORT );

        //topology conf
        TopologyBuilder builder = new TopologyBuilder();

        builder.setSpout( "KafkaMessage", new KafkaSpout( spoutConfig ) );
        builder.setBolt( "DataFilter", new DataFilterBolt()).shuffleGrouping( "KafkaMessage" );
        builder.setBolt( "SendMail", new SendMailBolt()).shuffleGrouping( "DataFilter", "send-mail");
        builder.setBolt( "WarnJudge", new WarnJudgeBolt() ).shuffleGrouping( "DataFilter","warn-judge" );
        builder.setBolt( "HbaseSave", hbaseBolt ).shuffleGrouping( "WarnJudge" ,"save-hbase");
        builder.setBolt( "MysqlSave15", jdbcInsert15Bolt).shuffleGrouping( "WarnJudge" ,"save-mysql-15");
        builder.setBolt( "MysqlSave10", jdbcInsert10Bolt).shuffleGrouping( "WarnJudge" ,"save-mysql-10");


        if (args != null && args.length > 0) {
            stormConf.setNumWorkers(1);
            StormSubmitter.submitTopology( ColdWarningDriver.class.getSimpleName(), stormConf, builder.createTopology() );
        } else {
            stormConf.setDebug(true);
            stormConf.setMaxTaskParallelism(3);
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology(ColdWarningDriver.class.getSimpleName(), stormConf, builder.createTopology());
        }


    }
}
