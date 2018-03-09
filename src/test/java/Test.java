// import com.batian.storm.tools.JsonUtil;
// import com.batian.storm.tools.WeatherBean;
// import org.apache.commons.lang.StringUtils;
//
// import java.io.FileReader;
// import java.util.ArrayList;
//
// /**
//  * Created by Ricky on 2018/3/8
//  *
//  * @author Administrator
//  */
// public class Test {
//     public static void main(String[] args) {
//         try {
//             JsonUtil jsonUtil = new JsonUtil(new FileReader( "data/data" ));
//             System.out.println(jsonUtil.getReason());
//             ArrayList<WeatherBean> weatherSeries = jsonUtil.getWeatherSeries();
//
//             System.out.println( StringUtils.join( weatherSeries.toArray(), "," ) );
//             String str = StringUtils.join( weatherSeries.toArray(), "," );
//
//
//
//
//
//
//
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//     }
// }
