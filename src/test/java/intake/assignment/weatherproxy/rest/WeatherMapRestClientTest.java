package intake.assignment.weatherproxy.rest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@RunWith(SpringRunner.class)
@RestClientTest
public class WeatherMapRestClientTest {
    @Autowired
    private MockRestServiceServer server;
    @Autowired
    private RestTemplateBuilder builder;

    @Test(expected = ResponseStatusException.class)
    public void  NoExistingCityApiCall_ThrowNotFound() {

        Assert.assertNotNull(this.builder);
        Assert.assertNotNull(this.server);

        RestTemplate restTemplate = this.builder.errorHandler(new RestResponseErrorHandler()).build();

        this.server.expect(once(), requestTo("/api/v1/weatherproxy/cities/abalalaa"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.NOT_FOUND));

        restTemplate.getForObject("/api/v1/weatherproxy/cities/abalalaa", String.class);
        this.server.verify();
    }
}
