package com.batian.storm.utils;


import com.batian.storm.conf.ConfigurationManager;
import com.batian.storm.conf.Constants;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 日期时间工具类
 * @author Administrator
 *
 */
public class DateUtils {

    /**
     * Number of milliseconds in a standard second.
     * @since 2.1
     */
    public static final long MILLIS_PER_SECOND = 1000;
    /**
     * Number of milliseconds in a standard minute.
     * @since 2.1
     */
    public static final long MILLIS_PER_MINUTE = 60 * MILLIS_PER_SECOND;
    /**
     * Number of milliseconds in a standard hour.
     * @since 2.1
     */
    public static final long MILLIS_PER_HOUR = 60 * MILLIS_PER_MINUTE;
    /**
     * Number of milliseconds in a standard day.
     * @since 2.1
     */
    public static final long MILLIS_PER_DAY = 24 * MILLIS_PER_HOUR;

    public static String getStartTime() {
        SimpleDateFormat df = new SimpleDateFormat( "yyyyMMdd" );
        String startTime = df.format( new Date() );
        return startTime;
    }

}
