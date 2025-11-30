package com.mpezzanera.app.whather_app.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Weather {
    private int weatherId;
    private String weatherMain;
    private String weatherDescription;
    private String weatherIcon;
    private int mainTemperature;
    private int mainFeelsLike;
    private int mainTempMin;
    private int mainTempMax;
    private int mainPressure;
    private int mainHumidity;
    private int timezone;
    private LocalDateTime timestamp;
    private LocalDateTime sunrise;
    private LocalDateTime sunset;
}
