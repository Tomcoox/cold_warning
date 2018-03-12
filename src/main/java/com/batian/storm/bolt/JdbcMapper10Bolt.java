package com.batian.storm.bolt;

import com.batian.storm.conf.ConfigurationManager;
import com.batian.storm.conf.Constants;
import org.apache.storm.guava.collect.Maps;
import org.apache.storm.jdbc.bolt.JdbcInsertBolt;
import org.apache.storm.jdbc.common.ConnectionProvider;
import org.apache.storm.jdbc.common.HikariCPConnectionProvider;
import org.apache.storm.jdbc.mapper.JdbcMapper;
import org.apache.storm.jdbc.mapper.SimpleJdbcMapper;

import java.util.Map;


/**
 * Created by Ricky on 2018/3/10
 *
 * @author Administrator
 */
public class JdbcMapper10Bolt {


    public JdbcInsertBolt getJdbcInsertBolt() {
        Map<String, Object> hikariConfigMap = Maps.newHashMap();
        hikariConfigMap.put("dataSourceClassName","com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        hikariConfigMap.put("dataSource.url", ConfigurationManager.getProperty( Constants.JDBC_URL ));
        hikariConfigMap.put("dataSource.user",ConfigurationManager.getProperty( Constants.JDBC_USER ));
        hikariConfigMap.put("dataSource.password",ConfigurationManager.getProperty( Constants.JDBC_PASSWORD ));
        ConnectionProvider connectionProvider = new HikariCPConnectionProvider(hikariConfigMap);
        String tableName = "cold_warning_10";
        JdbcMapper simpleJdbcMapper = new SimpleJdbcMapper(tableName, connectionProvider);
        return new JdbcInsertBolt( connectionProvider, simpleJdbcMapper )
                .withTableName( ConfigurationManager.getProperty( Constants.JDBC_TABLE_NAME_10 ) )
                .withQueryTimeoutSecs( 30 );
    }


}

