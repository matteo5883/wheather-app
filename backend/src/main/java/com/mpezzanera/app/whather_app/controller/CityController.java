package com.mpezzanera.app.whather_app.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mpezzanera.app.whather_app.model.City;
import com.mpezzanera.app.whather_app.service.OpenWeatherMapService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cities")
@RequiredArgsConstructor
@Tag(name = "City Controller", description = "Endpoints for managing and retrieving city information")
public class CityController {

    private final OpenWeatherMapService openWeatherMapService;

    @Operation(summary = "Search cities by name", description = "Retrieves a list of cities matching the provided name, state, and country.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of cities"),
            @ApiResponse(responseCode = "400", description = "Invalid input parameters"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<City>> getCities(
            @Parameter(description = "Name of the city", required = true) @RequestParam String name,
            @Parameter(description = "State code (optional)") @RequestParam(required = false) String state,
            @Parameter(description = "Country code (optional)") @RequestParam(required = false) String country,
            @Parameter(description = "Limit the number of results (default 5)") @RequestParam(defaultValue = "5") int limit) {
        
        List<City> cities = openWeatherMapService.getCityByName(name, state, country, limit);
        return ResponseEntity.ok(cities);
    }
}
