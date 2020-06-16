package intake.assignment.weatherproxy.rest.controller;

import intake.assignment.weatherproxy.entity.Weather;
import intake.assignment.weatherproxy.rest.controller.WeatherAPI;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class WeatherModelAssembler implements RepresentationModelAssembler<Weather, EntityModel<Weather>> {

    @Override
    public EntityModel<Weather> toModel(Weather weather) {
        return EntityModel.of(weather,
                WebMvcLinkBuilder.linkTo(methodOn(WeatherAPI.class).returnWeatherDataByCity(weather.getCityName())).withSelfRel(),
                linkTo(methodOn(WeatherAPI.class).returnAllWeatherData()).withRel("cities"));
    }
}

