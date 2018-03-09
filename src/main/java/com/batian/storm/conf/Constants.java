package com.batian.storm.conf;

/**
 * Created by Ricky on 2018/3/2
 *
 * @author Administrator
 */
public interface Constants {
    /**
     * App Configuration Constants
     */
    String QUERY_CYCLE_15 = "query.cycle.15";
    String QUERY_CYCLE_10 = "query.cycle.10";
    String QUERY_DATS = "query.days";
    String SUCCEED_CODE="succeed.code";

    /**
     * Project Configuration Constants
     */
    String JDBC_DRIVER = "jdbc.driver";
    String JDBC_DATASOURCE_SIZE = "jdbc.datasource.size";
    String JDBC_URL = "jdbc.url";
    String JDBC_USER = "jdbc.user";
    String JDBC_PASSWORD = "jdbc.password";

    /**
     * Kafka Configuration Constants
     */
    String ZK_LIST = "zk.list";
    String ZK_SERVER = "zk.server";
    String ZK_PORT = "zk.port";
    String KAFKA_TOPIC = "kafka.topic";
    String ZK_ROOT = "zk.root";
    String ZK_ID = "zk.id";

    /**
     * Hbase Configuration Constants
     */
    String ROOT_DIR = "hbase.rootdir";
    String HBASE_TABLE = "hbase.table";

}
