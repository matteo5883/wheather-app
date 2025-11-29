package com.mpezzanera.app.whather_app.model;

import java.util.List;

import lombok.Data;

@Data
public class City {
    private String name;
    private String country;
    private String countryCode;
    private double lat;
    private double lon;
    private List<String> locales;
}
