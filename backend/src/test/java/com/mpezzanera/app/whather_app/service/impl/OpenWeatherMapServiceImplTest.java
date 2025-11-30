package com.mpezzanera.app.whather_app.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mpezzanera.app.whather_app.mapper.WeatherMapper;
import com.mpezzanera.app.whather_app.model.City;
import com.mpezzanera.app.whather_app.model.Weather;
import com.mpezzanera.app.whather_app.model.WeatherOpenMap;

@RestClientTest
class OpenWeatherMapServiceImplTest {

    private OpenWeatherMapServiceImpl service;
    private MockRestServiceServer server;
    private ObjectMapper objectMapper = new ObjectMapper();
    private WeatherMapper weatherMapper = new WeatherMapper();

    @BeforeEach
    void setUp() {
        RestClient.Builder builder = RestClient.builder();
        builder.baseUrl("http://api.openweathermap.org");
        builder.defaultUriVariables(Map.of("appId", "test-api-key"));
        server = MockRestServiceServer.bindTo(builder).build();
        service = new OpenWeatherMapServiceImpl(builder.build(), weatherMapper);
    }

    @Test
    void getCityByName_shouldReturnListOfCities() throws JsonProcessingException {
        // Arrange
        City city = new City();
        city.setName("London");
        city.setCountry("GB");
        List<City> expectedResponse = List.of(city);

        String responseJson = objectMapper.writeValueAsString(expectedResponse);

        server.expect(requestTo("http://api.openweathermap.org/geo/1.0/direct?q=London,GB&limit=5&appid=test-api-key"))
                .andRespond(withSuccess(responseJson, MediaType.APPLICATION_JSON));

        // Act
        List<City> result = service.getCityByName("London", null, "GB", 5);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("London", result.get(0).getName());
    }

    @Test
    void getWeather_shouldReturnWeather() throws JsonProcessingException {
        // Arrange
        WeatherOpenMap weatherOpenMap = new WeatherOpenMap();
        weatherOpenMap.setName("Milan");
        WeatherOpenMap.Main main = new WeatherOpenMap.Main();
        main.setTemp(20.5);
        weatherOpenMap.setMain(main);

        String responseJson = objectMapper.writeValueAsString(weatherOpenMap);

        server.expect(requestTo(
                "http://api.openweathermap.org/data/2.5/weather?lat=45.4642&lon=9.19&units=metric&lang=en&appid=test-api-key"))
                .andRespond(withSuccess(responseJson, MediaType.APPLICATION_JSON));

        // Act
        Weather result = service.getWeather(45.4642, 9.19, null, null);

        // Assert
        assertNotNull(result);
        assertEquals(21, result.getMainTemperature());
    }

    @Test
    void getWeather_shouldUseProvidedUnitsAndLang() throws JsonProcessingException {
        // Arrange
        WeatherOpenMap weatherOpenMap = new WeatherOpenMap();

        String responseJson = objectMapper.writeValueAsString(weatherOpenMap);

        server.expect(requestTo(
                "http://api.openweathermap.org/data/2.5/weather?lat=45.4642&lon=9.19&units=imperial&lang=it&appid=test-api-key"))
                .andRespond(withSuccess(responseJson, MediaType.APPLICATION_JSON));

        // Act
        Weather result = service.getWeather(45.4642, 9.19, "imperial", "it");

        // Assert
        assertNotNull(result);
    }
}
