package intake.assignment.weatherproxy.repo;

import intake.assignment.weatherproxy.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WeatherRepo extends JpaRepository<Weather, Long>, WeatherRepoCustom {

}
