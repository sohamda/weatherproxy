package intake.assignment.weatherproxy.repo;

import intake.assignment.weatherproxy.entity.Weather;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Transactional
public class WeatherRepoCustomImpl implements WeatherRepoCustom {

    @PersistenceContext
    EntityManager entityManager;

    private final String SELECT_QUERY = "SELECT w.* FROM WEATHER as w WHERE w.city_name like ?";
    private final String DELETE_QUERY = "DELETE FROM WEATHER w WHERE w.city_name like ? ";

    @Override
    public Optional<Weather> findByCityName(String cityName) {
        return Optional.ofNullable(getWeatherByCityName(cityName));
    }

    @Override
    public void deleteByCityName(String cityName) {
        Query query = entityManager.createNativeQuery(DELETE_QUERY, Weather.class);
        query.setParameter(1, "%" + cityName + "%");
        query.executeUpdate();
    }

    private Weather getWeatherByCityName(String cityName) {
        Weather weatherRetrieved = null;
        Query query = entityManager.createNativeQuery(SELECT_QUERY, Weather.class);
        query.setParameter(1, "%" + cityName + "%");
        List<Weather> matchedWeatherRecords = query.getResultList();

        if(!matchedWeatherRecords.isEmpty()) {
            weatherRetrieved = matchedWeatherRecords.get(0);
        }
        return weatherRetrieved;
    }
}
