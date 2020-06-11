package intake.assignment.weatherproxy.entity;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="WEATHER")
public class Weather {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name="city_name")
    private String cityName;
    @Column(name="temp_min")
    private Double tempMin;
    @Column(name="temp_max")
    private Double tempMax;
    @Column(name="sunrise")
    private Long sunrise;
    @Column(name="date_registry")
    private Date dateOfRegistry;

    public Weather(Long id, String cityName, Double tempMin, Double tempMax, Long sunrise, Date dateOfRegistry) {
        this.id = id;
        this.cityName = cityName;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.sunrise = sunrise;
        this.dateOfRegistry = dateOfRegistry;
    }

    public Weather() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Double getTempMin() {
        return tempMin;
    }

    public void setTempMin(Double tempMin) {
        this.tempMin = tempMin;
    }

    public Double getTempMax() {
        return tempMax;
    }

    public void setTempMax(Double tempMax) {
        this.tempMax = tempMax;
    }

    public Long getSunrise() {
        return sunrise;
    }

    public void setSunrise(Long sunrise) {
        this.sunrise = sunrise;
    }

    public Date getDateOfRegistry() {
        return dateOfRegistry;
    }

    public void setDateOfRegistry(Date dateOfRegistry) {
        this.dateOfRegistry = dateOfRegistry;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "id=" + id +
                ", cityName='" + cityName + '\'' +
                ", tempMin=" + tempMin +
                ", tempMax=" + tempMax +
                ", sunrise=" + sunrise +
                ", dateOfRegistry=" + dateOfRegistry +
                '}';
    }
}
