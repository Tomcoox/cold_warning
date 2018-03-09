package com.batian.storm.tools;

/**
 * Created by Ricky on 2018/3/8
 *
 * @author Administrator
 */
public class WeatherBean {
    private String cw_am;
    private String w_am;
    private String cw_pm;
    private String w_pm;
    private String wd;
    private String wind;
    private String cwd;
    private int tmp_max;
    private int tmp_min;
    private String sunrise;
    private String sunset;

    @Override
    public String toString() {
        return "WeatherBean{" +
                "cw_am='" + cw_am + '\'' +
                ", w_am='" + w_am + '\'' +
                ", cw_pm='" + cw_pm + '\'' +
                ", w_pm='" + w_pm + '\'' +
                ", wd='" + wd + '\'' +
                ", wind='" + wind + '\'' +
                ", cwd='" + cwd + '\'' +
                ", tmp_max=" + tmp_max +
                ", tmp_min=" + tmp_min +
                ", sunrise='" + sunrise + '\'' +
                ", sunset='" + sunset + '\'' +
                '}';
    }

    public String getCw_am() {
        return cw_am;
    }

    public void setCw_am(String cw_am) {
        this.cw_am = cw_am;
    }

    public String getW_am(String w_am) {
        return this.w_am;
    }

    public void setW_am(String w_am) {
        this.w_am = w_am;
    }

    public String getCw_pm() {
        return cw_pm;
    }

    public void setCw_pm(String cw_pm) {
        this.cw_pm = cw_pm;
    }

    public String getW_pm() {
        return w_pm;
    }

    public void setW_pm(String w_pm) {
        this.w_pm = w_pm;
    }

    public String getWd() {
        return wd;
    }

    public void setWd(String wd) {
        this.wd = wd;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getCwd() {
        return cwd;
    }

    public void setCwd(String cwd) {
        this.cwd = cwd;
    }

    public int getTmp_max() {
        return tmp_max;
    }

    public void setTmp_max(int tmp_max) {
        this.tmp_max = tmp_max;
    }

    public int getTmp_min() {
        return tmp_min;
    }

    public void setTmp_min(int tmp_min) {
        this.tmp_min = tmp_min;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }
}
