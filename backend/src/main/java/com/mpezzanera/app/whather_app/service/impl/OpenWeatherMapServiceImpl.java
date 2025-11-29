package com.mpezzanera.app.whather_app.service.impl;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;

import com.mpezzanera.app.whather_app.model.City;
import com.mpezzanera.app.whather_app.service.OpenWeatherMapService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OpenWeatherMapServiceImpl implements OpenWeatherMapService {

    private final RestClient restClient;

    private boolean isNullString(String str) {
        return !StringUtils.hasLength(str) || (str != null && str.trim().isEmpty());
    }

    @Override
    public List<City> getCityByName(String cityName, String stateCode, String countryCode, int limit) {
        if (isNullString(cityName)) {
            throw new IllegalArgumentException("City name must not be null or empty");
        }

        String query = buildQuery(cityName, stateCode, countryCode);
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/geo/1.0/direct")
                        .queryParam("q", query)
                        .queryParam("limit", limit > 0 ? limit : 5)
                        .build())
                .retrieve()
                .body(new ParameterizedTypeReference<List<City>>() {
                });
    }

    private String buildQuery(String cityName, String stateCode, String countryCode) {
        StringBuilder queryBuilder = new StringBuilder(cityName);
        if (!isNullString(stateCode)) {
            queryBuilder.append(",").append(stateCode);
        }
        if (!isNullString(countryCode)) {
            queryBuilder.append(",").append(countryCode);
        }
        return queryBuilder.toString();
    }

}
