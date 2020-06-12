package intake.assignment.weatherproxy.repo;

import intake.assignment.weatherproxy.entity.Weather;

import java.util.Optional;

public interface WeatherRepoCustom {

    Weather addCityWeather(Weather weather);

    Optional<Weather> findByCityName(String cityName);

    void deleteByCityName(String cityName);
}
