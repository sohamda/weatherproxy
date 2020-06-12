package intake.assignment.weatherproxy.repo;

import intake.assignment.weatherproxy.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherRepo extends JpaRepository<Weather, Long>, WeatherRepoCustom {

}
