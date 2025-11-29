package com.mpezzanera.app.whather_app.controller;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.mpezzanera.app.whather_app.config.SecurityConfig;
import com.mpezzanera.app.whather_app.model.City;
import com.mpezzanera.app.whather_app.service.OpenWeatherMapService;

@WebMvcTest(CityController.class)
@Import(SecurityConfig.class)
class CityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OpenWeatherMapService openWeatherMapService;

    @Test
    void getCities_shouldReturnListOfCities() throws Exception {
        // Arrange
        City city = new City();
        city.setName("London");
        city.setCountry("GB");
        List<City> cities = List.of(city);

        when(openWeatherMapService.getCityByName(anyString(), anyString(), anyString(), anyInt()))
                .thenReturn(cities);

        // Act & Assert
        mockMvc.perform(get("/api/cities")
                .param("name", "London")
                .param("state", "")
                .param("country", "GB")
                .param("limit", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("London"))
                .andExpect(jsonPath("$[0].country").value("GB"));
    }
    
    @Test
    void getCities_shouldReturnBadRequest_whenNameIsMissing() throws Exception {
        mockMvc.perform(get("/api/cities")
                .param("country", "GB"))
                .andExpect(status().isBadRequest());
    }
}
