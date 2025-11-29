package com.mpezzanera.app.whather_app.model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class City {
    private String name;
    private String country;
    private String state;
    private double lat;
    private double lon;

    @JsonProperty("local_names")
    private Map<String, String> locales;
}
