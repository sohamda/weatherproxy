package intake.assignment.weatherproxy.rest;

import intake.assignment.weatherproxy.entity.Weather;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class WeatherModelAssembler implements RepresentationModelAssembler<Weather, EntityModel<Weather>> {

    @Override
    public EntityModel<Weather> toModel(Weather weather) {
        return EntityModel.of(weather,
                linkTo(methodOn(WeatherAPI.class).one(weather.getCityName())).withSelfRel(),
                linkTo(methodOn(WeatherAPI.class).all()).withRel("employees"));
    }
}

