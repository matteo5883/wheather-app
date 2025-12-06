package com.mpezzanera.app.whather_app.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mpezzanera.app.whather_app.model.Weather;
import com.mpezzanera.app.whather_app.service.OpenWeatherMapService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/weather")
@RequiredArgsConstructor
@Tag(name = "Weather Controller", description = "Endpoints for retrieving weather information")
public class WeatherController {

    private final OpenWeatherMapService openWeatherMapService;

    @Operation(summary = "Get weather by coordinates", description = "Retrieves current weather data for a specific location defined by latitude and longitude.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved weather data"),
            @ApiResponse(responseCode = "400", description = "Invalid input parameters"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<Weather> getWeather(
            @Parameter(description = "Latitude", required = true) @RequestParam double lat,
            @Parameter(description = "Longitude", required = true) @RequestParam double lon,
            @Parameter(description = "Units of measurement (standard, metric, imperial). Default: metric") @RequestParam(required = false, defaultValue = "metric") String units,
            @Parameter(description = "Language code (e.g., en, it). Default: en") @RequestParam(required = false, defaultValue = "en") String lang) {

        Weather weather = openWeatherMapService.getWeather(lat, lon, units, lang);
        return ResponseEntity.ok(weather);
    }
}
