package com.mpezzanera.app.whather_app.mapper;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.stereotype.Component;

import com.mpezzanera.app.whather_app.model.Weather;
import com.mpezzanera.app.whather_app.model.WeatherOpenMap;

@Component
public class WeatherMapper {

    public Weather toWeather(WeatherOpenMap source) {
        if (source == null) {
            return null;
        }

        Weather target = new Weather();

        if (source.getWeather() != null && !source.getWeather().isEmpty()) {
            WeatherOpenMap.Weather weatherInfo = source.getWeather().get(0);
            target.setWeatherId(weatherInfo.getId());
            target.setWeatherMain(weatherInfo.getMain());
            target.setWeatherDescription(weatherInfo.getDescription());
            target.setWeatherIcon(weatherInfo.getIcon());
        }

        if (source.getMain() != null) {
            target.setMainTemperature((int) Math.round(source.getMain().getTemp()));
            target.setMainFeelsLike((int) Math.round(source.getMain().getFeelsLike()));
            target.setMainTempMin((int) Math.round(source.getMain().getTempMin()));
            target.setMainTempMax((int) Math.round(source.getMain().getTempMax()));
            target.setMainPressure(source.getMain().getPressure());
            target.setMainHumidity(source.getMain().getHumidity());
        }

        target.setTimezone(source.getTimezone());
        target.setTimestamp(unixToLocalDateTime(source.getDt()));

        if (source.getSys() != null) {
            target.setSunrise(unixToLocalDateTime(source.getSys().getSunrise()));
            target.setSunset(unixToLocalDateTime(source.getSys().getSunset()));
        }

        return target;
    }

    private LocalDateTime unixToLocalDateTime(long unixSeconds) {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(unixSeconds), ZoneId.systemDefault());
    }
}
