package com.batian.storm.tools;

/**
 * Created by Ricky on 2018/3/9
 *
 * @author Administrator
 */
public class WarningBean {
    private String cityKey;
    private String warnTime;
    private String warnCycle;
    private String tmpAvg;
    private String tmpMin;
    private String tmpMax;



    public String getCityKey() {
        return cityKey;
    }

    public void setCityKey(String cityKey) {
        this.cityKey = cityKey;
    }

    public String getWarnTime() {
        return warnTime;
    }

    public void setWarnTime(String warnTime) {
        this.warnTime = warnTime;
    }

    public String getWarnCycle() {
        return warnCycle;
    }

    public void setWarnCycle(String warnCycle) {
        this.warnCycle = warnCycle;
    }

    public String getTmpAvg() {
        return tmpAvg;
    }

    public void setTmpAvg(String tmpAvg) {
        this.tmpAvg = tmpAvg;
    }

    public String getTmpMin() {
        return tmpMin;
    }

    public void setTmpMin(String tmpMin) {
        this.tmpMin = tmpMin;
    }

    public String getTmpMax() {
        return tmpMax;
    }

    public void setTmpMax(String tmpMax) {
        this.tmpMax = tmpMax;
    }
}
