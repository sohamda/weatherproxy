package intake.assignment.weatherproxy.rest;

import intake.assignment.weatherproxy.entity.Weather;
import intake.assignment.weatherproxy.repo.WeatherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/weatherproxy")
public class WeatherAPI {

    @Autowired
    private WeatherRepo weatherRepo;
    @Autowired
    private WeatherModelAssembler weatherModelAssembler;

    @GetMapping("/cities")
    CollectionModel<EntityModel<Weather>> all() {

        List<EntityModel<Weather>> allWeatherInfo = weatherRepo.findAll().stream().map(weatherModelAssembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(allWeatherInfo, linkTo(methodOn(WeatherAPI.class).all()).withSelfRel());
    }

    @PostMapping("/cities/{cityname}")
    EntityModel<Weather> newCityWeather(@PathVariable String cityname, @RequestBody Weather newCityWeather) {
        Weather  weatherInfo = weatherRepo.save(newCityWeather);
        return weatherModelAssembler.toModel(weatherInfo);
    }

    @GetMapping("/cities/{cityname}")
    EntityModel<Weather> one(@PathVariable String cityname) {
        Weather weatherInfo = weatherRepo.findByCityName(cityname)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "City Not Found"));
        return weatherModelAssembler.toModel(weatherInfo);
    }
    @DeleteMapping("/cities/{cityname}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    void deleteCity(@PathVariable String cityname) {
        weatherRepo.deleteByCityName(cityname);
    }
}
