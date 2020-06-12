package intake.assignment.weatherproxy.rest;

import intake.assignment.weatherproxy.entity.Weather;
import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class WeatherMapRestClient {

    private final String WEATHERMAP_API = "http://api.openweathermap.org/data/2.5/weather?appid={appid}&q={cityname}&units={unit}";
    private final String APP_ID = "e617cfacae8e5b0dfc77a7b8044c3d1d";
    private final String UNIT = "metric";

    public Weather getWeathermapResponse(String cityName) {

        Map<String, String> params = new HashMap<String, String>();
        params.put("cityname", cityName);
        params.put("appid", APP_ID);
        params.put("unit", UNIT);

        RestTemplate restTemplate = new RestTemplate();
        String jsonResponse = restTemplate.getForObject(WEATHERMAP_API, String.class, params);

        return extractWeatherObject(jsonResponse);
    }

    private Weather extractWeatherObject(String jsonString) {
        JSONObject weathermapResponse = new JSONObject(jsonString);
        Double tempMin = weathermapResponse.getJSONObject("main").getDouble("temp_min");
        Double tempMax = weathermapResponse.getJSONObject("main").getDouble("temp_max");
        Long sunrise = weathermapResponse.getJSONObject("sys").getLong("sunrise");
        String cityName = weathermapResponse.getString("name");
        return new Weather(cityName.toLowerCase(), tempMin, tempMax, sunrise);
    }
}
