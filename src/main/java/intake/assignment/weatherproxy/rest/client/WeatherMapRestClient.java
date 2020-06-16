package intake.assignment.weatherproxy.rest.client;

import intake.assignment.weatherproxy.entity.Weather;
import intake.assignment.weatherproxy.rest.handler.RestResponseErrorHandler;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class WeatherMapRestClient {

    private final String WEATHERMAP_API = "http://api.openweathermap.org/data/2.5/weather?appid={appid}&q={cityname}&units={unit}";
    private final String UNIT = "metric";

    @Value( "${openweathermap.appid}" )
    private String appid;

    public Weather getWeathermapResponse(String cityName) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("cityname", cityName);
        params.put("appid", appid);
        params.put("unit", UNIT);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new RestResponseErrorHandler());
        String jsonResponse = restTemplate.getForObject(WEATHERMAP_API, String.class, params);

        return extractWeatherObject(jsonResponse);
    }

    protected Weather extractWeatherObject(String jsonString) {
        JSONObject weathermapResponse = new JSONObject(jsonString);
        Double tempMin = weathermapResponse.getJSONObject("main").getDouble("temp_min");
        Double tempMax = weathermapResponse.getJSONObject("main").getDouble("temp_max");
        Long sunrise = weathermapResponse.getJSONObject("sys").getLong("sunrise");
        String cityName = weathermapResponse.getString("name");
        return new Weather(cityName.toLowerCase(), tempMin, tempMax, sunrise);
    }
}
