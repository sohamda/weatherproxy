package intake.assignment.weatherproxy.rest.controller;

import intake.assignment.weatherproxy.entity.Weather;
import intake.assignment.weatherproxy.repo.WeatherRepo;
import intake.assignment.weatherproxy.rest.client.WeatherMapRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
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
    @Autowired
    private WeatherMapRestClient weatherMapRestClient;

    @GetMapping("/cities")
    CollectionModel<EntityModel<Weather>> returnAllWeatherData() {
        List<EntityModel<Weather>> allWeatherInfo = weatherRepo.findAll().stream().map(weatherModelAssembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(allWeatherInfo, linkTo(methodOn(WeatherAPI.class).returnAllWeatherData()).withSelfRel());
    }

    @PostMapping("/cities/{cityname}")
    EntityModel<Weather> insertNewWeatherDataByCity(@PathVariable String cityname) {
        Weather  weatherInfoRetrieved = weatherMapRestClient.getWeathermapResponse(cityname);
        // remove any existing entry
        weatherRepo.deleteByCityName(cityname);
        //insert the retrieved info
        Weather  weatherInfoSaved = weatherRepo.save(weatherInfoRetrieved);
        return weatherModelAssembler.toModel(weatherInfoSaved);
    }

    @GetMapping("/cities/{cityname}")
    EntityModel<Weather> returnWeatherDataByCity(@PathVariable String cityname) {
        Weather weatherInfo = weatherRepo.findByCityName(cityname)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "City Not Found"));
        return weatherModelAssembler.toModel(weatherInfo);
    }
    @DeleteMapping("/cities/{cityname}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    void deleteWeatherDataByCity(@PathVariable String cityname) {
        weatherRepo.deleteByCityName(cityname);
    }
}
