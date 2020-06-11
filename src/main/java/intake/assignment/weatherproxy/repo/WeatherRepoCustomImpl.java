package intake.assignment.weatherproxy.repo;

import intake.assignment.weatherproxy.entity.Weather;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class WeatherRepoCustomImpl implements WeatherRepoCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Optional<Weather> findByCityName(String cityName) {
        Query query = entityManager.createNativeQuery("SELECT w.* FROM WEATHER as w WHERE w.city_name = ?", Weather.class);
        query.setParameter(1, cityName);
        List<Weather> matchedWeatherRecords = query.getResultList();

        if(!matchedWeatherRecords.isEmpty()) {
            return Optional.of(matchedWeatherRecords.get(0));
        }
        return Optional.empty();

    }

    @Override
    public void deleteByCityName(String cityName) {
        Query query = entityManager.createNativeQuery("delete from WEATHER w where w.city_name = ? ", Weather.class);
        query.setParameter(1, cityName);
        query.executeUpdate();
    }
}
