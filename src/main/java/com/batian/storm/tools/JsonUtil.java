package com.batian.storm.tools;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.util.ArrayList;

/**
 * Created by Ricky on 2018/3/8
 *
 * @author Administrator
 */
public class JsonUtil {
    private  JsonParser jsonParser = new JsonParser();
    private JsonObject json;

    public JsonUtil(String data)  throws Exception{
        this.json = (JsonObject) jsonParser.parse( data );
    }


    public String getReason() {
        return json.get( "reason" ).getAsString();

    }

    public int getErrorCode() {
        return json.get( "error_code" ).getAsInt();
    }

    public String getStartTime() {
        JsonObject result = json.get( "result" ).getAsJsonObject();
        return result.get( "startTime" ).getAsString();
    }

    public ArrayList<WeatherBean> getWeatherSeries() {
        ArrayList<WeatherBean> weatherSeries = new ArrayList<WeatherBean>();
        JsonObject result = json.get( "result" ).getAsJsonObject();
        JsonArray series = result.get( "series" ).getAsJsonArray();
        for (int i = 0; i < series.size(); i++) {
            WeatherBean weatherBean = new WeatherBean();
            JsonObject dayObject = series.get( i ).getAsJsonObject();
            weatherBean.setCw_am( dayObject.get( "cw_am" ).getAsString() );
            weatherBean.setW_am( dayObject.get( "w_am" ).getAsString()  );
            weatherBean.setCw_pm( dayObject.get( "cw_pm" ).getAsString()  );
            weatherBean.setW_pm( dayObject.get( "w_pm" ).getAsString()  );
            weatherBean.setWd( dayObject.get( "wd" ).getAsString()  );
            weatherBean.setWind( dayObject.get( "wind" ).getAsString()  );
            weatherBean.setCwd( dayObject.get( "cwd" ).getAsString()  );
            weatherBean.setTmp_max( dayObject.get( "tmp_max" ).getAsInt() );
            weatherBean.setTmp_min( dayObject.get( "tmp_min" ).getAsInt() );
            weatherBean.setSunrise( dayObject.get( "sunrise" ).getAsString()  );
            weatherBean.setSunset( dayObject.get( "sunset" ).getAsString() );
            weatherSeries.add( weatherBean );
        }
        return weatherSeries;
    }

}
