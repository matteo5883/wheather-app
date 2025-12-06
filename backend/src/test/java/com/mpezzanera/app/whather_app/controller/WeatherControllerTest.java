package com.mpezzanera.app.whather_app.controller;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.mpezzanera.app.whather_app.config.SecurityConfig;
import com.mpezzanera.app.whather_app.model.Weather;
import com.mpezzanera.app.whather_app.service.OpenWeatherMapService;

@WebMvcTest(WeatherController.class)
@Import(SecurityConfig.class)
class WeatherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OpenWeatherMapService openWeatherMapService;

    @Test
    void getWeather_shouldReturnWeather() throws Exception {
        // Arrange
        Weather weather = new Weather();
        weather.setWeatherMain("Clear");
        weather.setMainTemperature(25);

        when(openWeatherMapService.getWeather(anyDouble(), anyDouble(), anyString(), anyString()))
                .thenReturn(weather);

        // Act & Assert
        mockMvc.perform(get("/api/v1/weather")
                .param("lat", "45.4642")
                .param("lon", "9.1900")
                .param("units", "metric")
                .param("lang", "en"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.weatherMain").value("Clear"))
                .andExpect(jsonPath("$.mainTemperature").value(25));
    }

    @Test
    void getWeather_shouldReturnBadRequest_whenCoordinatesAreMissing() throws Exception {
        mockMvc.perform(get("/api/v1/weather")
                .param("units", "metric"))
                .andExpect(status().isBadRequest());
    }
}
