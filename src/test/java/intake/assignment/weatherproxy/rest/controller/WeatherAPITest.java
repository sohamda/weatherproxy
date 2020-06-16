package intake.assignment.weatherproxy.rest.controller;

import intake.assignment.weatherproxy.entity.Weather;
import intake.assignment.weatherproxy.repo.WeatherRepo;
import intake.assignment.weatherproxy.rest.client.WeatherMapRestClient;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.doNothing;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.anyString;
import static org.hamcrest.Matchers.is;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WeatherAPI.class)
public class WeatherAPITest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private WeatherRepo weatherRepo;
    @MockBean
    private WeatherMapRestClient weatherMapRestClient;
    @MockBean
    private WeatherModelAssembler weatherModelAssembler;

    @Test
    public void getAllWeatherDataTest() throws Exception{

        Assert.assertNotNull(this.mvc);

        Weather amsterdamWeather = new Weather("amsterdam", 21.3, 23.5, 234522L);
        List<Weather> allWeather = Arrays.asList(amsterdamWeather);
        given(weatherRepo.findAll()).willReturn(allWeather);

        when(weatherModelAssembler.toModel(any())).thenCallRealMethod();

        mvc.perform(get("/api/v1/weatherproxy/cities")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$._embedded.weatherList[0].cityName", is("amsterdam")))
                .andExpect(jsonPath("$._embedded.weatherList[0]._links.self.href").exists())
                .andExpect(jsonPath("$._embedded.weatherList[0]._links.cities.href").exists())
                .andExpect(jsonPath("$._links.self.href").exists());
    }

    @Test
    public void insertWeatherDataTest() throws Exception {

        Assert.assertNotNull(this.mvc);

        Weather amsterdamWeather = new Weather("amsterdam", 21.3, 23.5, 234522L);

        given(weatherMapRestClient.getWeathermapResponse(anyString())).willReturn(amsterdamWeather);
        doNothing().when(weatherRepo).deleteByCityName(anyString());
        given(weatherRepo.save(any())).willReturn(amsterdamWeather);

        when(weatherModelAssembler.toModel(any())).thenCallRealMethod();

        mvc.perform(post("/api/v1/weatherproxy/cities/amsterdam")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.cityName", is("amsterdam")))
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$._links.cities.href").exists());
    }
}
