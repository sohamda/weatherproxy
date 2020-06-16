# Weather Proxy - 

Weather Proxy is a Spring boot application that acts as a proxy for an online weather service (https://openweathermap.org/api). 

## Usage

```bash
mvn clean install

java -jar ./target/weatherproxy-0.0.1-SNAPSHOT.jar --spring.config.location=./weatherapi.properties
```

## Testing
You can use POSTMAN to test the API endpoints

File : WeatherProxy.postman_collection.json

## API Details
- Exposes
  - POST - /api/v1/weatherproxy/cities/{cityname}
    - Calls "openweathermap" and stores the result in an in-memory db.
  - GET - /api/v1/weatherproxy/cities/
    - Returns all weather data stored in the in-memory db.
  - GET - /api/v1/weatherproxy/cities/{cityname}
    - Returns the weather data stored in the in-memory db.
  - DELETE - /api/v1/weatherproxy/cities/{cityname}

## Application Details
- Application uses an H2 in-memory database. 
  - Details are in weatherapi.properties (and application.properties) file.
- It uses an @Entity to store:
  - id
  - name
  - temp_min
  - temp_max
  - sunrise
- Exposes a few unit tests
  - to mock the response of POST and GET. 
  - check RestTemplate for 404 response.

