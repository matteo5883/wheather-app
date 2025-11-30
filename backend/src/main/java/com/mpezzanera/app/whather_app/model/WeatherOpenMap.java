package com.mpezzanera.app.whather_app.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class WeatherOpenMap {

    private Coord coord;
    private List<Weather> weather;
    private String base;
    private Main main;
    private int visibility;
    private Wind wind;
    private Clouds clouds;
    private long dt;
    private Sys sys;
    private int timezone;
    private long id;
    private String name;
    private int cod;

    @Data
    public static class Coord {
        private double lon;
        private double lat;
    }

    @Data
    public static class Weather {
        private int id;
        private String main;
        private String description;
        private String icon;
    }

    @Data
    public static class Main {
        private double temp;

        @JsonProperty("feels_like")
        private double feelsLike;

        @JsonProperty("temp_min")
        private double tempMin;

        @JsonProperty("temp_max")
        private double tempMax;

        private int pressure;
        private int humidity;

        @JsonProperty("sea_level")
        private int seaLevel;

        @JsonProperty("grnd_level")
        private int grndLevel;
    }

    @Data
    public static class Wind {
        private double speed;
        private int deg;
    }

    @Data
    public static class Clouds {
        private int all;
    }

    @Data
    public static class Sys {
        private int type;
        private long id;
        private String country;
        private long sunrise;
        private long sunset;
    }
}
