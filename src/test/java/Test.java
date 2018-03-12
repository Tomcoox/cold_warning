import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.utils.Utils;
import com.batian.storm.bolt.*;
import com.batian.storm.conf.ConfigurationManager;
import com.batian.storm.conf.Constants;
import org.apache.storm.hbase.bolt.HBaseBolt;
import org.apache.storm.jdbc.bolt.JdbcInsertBolt;

import java.util.HashMap;

/**
 * Created by Ricky on 2018/3/8
 *
 * @author Administrator
 */
public class Test {
    public static void main(String[] args)  {

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

        // //kafka conf
        // BrokerHosts brokerHosts = new ZkHosts( ConfigurationManager.getProperty( Constants.ZK_SERVER ) );
        //
        // //Spout conf
        // SpoutConfig spoutConfig = new SpoutConfig( brokerHosts, ConfigurationManager.getProperty( Constants.KAFKA_TOPIC ),
        //         ConfigurationManager.getProperty( Constants.ZK_ROOT ), ConfigurationManager.getProperty( Constants.ZK_ID ) );
        // //spout model
        // spoutConfig.scheme =new SchemeAsMultiScheme( new StringScheme() );
        // spoutConfig.forceFromStart = false;
        // //zk
        // spoutConfig.zkServers = Collections.singletonList( ConfigurationManager.getProperty( Constants.ZK_LIST ) );
        // spoutConfig.zkPort = ConfigurationManager.getInteger( Constants.ZK_PORT );

        //topology conf
        TopologyBuilder builder = new TopologyBuilder();

        builder.setSpout( "KafkaMessage", new Spout() );
        builder.setBolt( "DataFilter", new DataFilterBolt()).shuffleGrouping( "KafkaMessage" );
        builder.setBolt( "SendMail", new SendMailBolt()).shuffleGrouping( "DataFilter", "send-mail");
        builder.setBolt( "WarnJudge", new WarnJudgeBolt() ).shuffleGrouping( "DataFilter","warn-judge" );
        builder.setBolt( "HbaseSave", hbaseBolt ).shuffleGrouping( "WarnJudge" ,"save-hbase");
        builder.setBolt( "MysqlSave15", jdbcInsert15Bolt).shuffleGrouping( "WarnJudge" ,"save-mysql-15");
        builder.setBolt( "MysqlSave10", jdbcInsert10Bolt).shuffleGrouping( "WarnJudge" ,"save-mysql-10");


        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology(Test.class.getSimpleName(),stormConf,builder.createTopology());
        Utils.sleep(100000);
        cluster.killTopology(Test.class.getSimpleName());
        cluster.shutdown();



    }
}
