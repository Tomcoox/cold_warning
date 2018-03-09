package com.batian.storm.tools;

import com.batian.storm.conf.ConfigurationManager;
import com.batian.storm.conf.Constants;

import java.util.ArrayList;

/**
 * Created by Ricky on 2018/3/8
 *
 * @author Administrator
 */
public class WarnOperation {
    private ArrayList<WeatherBean> weatherBeans;
    private int queryDays = ConfigurationManager.getInteger( Constants.QUERY_DATS );

    public WarnOperation(ArrayList<WeatherBean> weatherBeans) {
        this.weatherBeans = weatherBeans;
    }


    public boolean isCold(int cycle) {
        int maxTemperature = getMaxTemperature( cycle );
        int vagTemperature = getAvgTemperature( cycle );
        return maxTemperature == queryDays || vagTemperature == queryDays;
    }

    public int getTmpMin(int cycle) {
        int tmpMin = 0;
        for (int i = cycle - queryDays ; i < cycle; i++) {
            if (weatherBeans.get( i ).getTmp_min() < tmpMin) {
                tmpMin = weatherBeans.get( i ).getTmp_min();
            }
        }
        return tmpMin;
    }

    public int getTmpMax(int cycle) {
        int tmpMax = 0;
        for (int i = cycle - queryDays ; i < cycle; i++) {
            if (weatherBeans.get( i ).getTmp_max() < tmpMax) {
                tmpMax = weatherBeans.get( i ).getTmp_max();
            }
        }
        return tmpMax;
    }

    public int getTmpAvg(int cycle) {
        int tmpAvg = 0;
        for (int i = cycle - queryDays; i < cycle; i++) {
            int max = weatherBeans.get( i ).getTmp_max();
            int min = weatherBeans.get( i ).getTmp_min();
            int vag = (max + min) / 2;
            tmpAvg += vag;
        }
            return tmpAvg / queryDays;
    }


    private int getAvgTemperature(int cycle){
        int sum = 0;
        for (int i = cycle - queryDays ; i < cycle; i++) {
            int max = weatherBeans.get( i ).getTmp_max();
            int min = weatherBeans.get( i ).getTmp_min();
            int vag = (max + min) / 2;
            if (vag <= 12) {
                sum++;
            }
        }
        return sum;
    }

    private int getMaxTemperature(int cycle) {
        int sum = 0;
        for (int i = cycle - queryDays; i < cycle; i++) {
            int max = weatherBeans.get( i ).getTmp_max();
            if (max <= 10) {
                sum++;
            }
        }
        return sum;
    }



}
