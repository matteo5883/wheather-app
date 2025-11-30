package com.mpezzanera.app.whather_app.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.mpezzanera.app.whather_app.model.Weather;
import com.mpezzanera.app.whather_app.model.WeatherOpenMap;

class WeatherMapperTest {

    private final WeatherMapper mapper = new WeatherMapper();

    @Test
    void toWeather_shouldMapFieldsCorrectly() {
        // Arrange
        WeatherOpenMap source = new WeatherOpenMap();

        WeatherOpenMap.Weather weatherInfo = new WeatherOpenMap.Weather();
        weatherInfo.setId(800);
        weatherInfo.setMain("Clear");
        weatherInfo.setDescription("clear sky");
        weatherInfo.setIcon("01d");
        source.setWeather(List.of(weatherInfo));

        WeatherOpenMap.Main main = new WeatherOpenMap.Main();
        main.setTemp(20.5);
        main.setFeelsLike(19.8);
        main.setTempMin(18.2);
        main.setTempMax(22.1);
        main.setPressure(1013);
        main.setHumidity(55);
        source.setMain(main);

        source.setTimezone(3600);
        source.setDt(1696161600L); // 2023-10-01 12:00:00 UTC

        WeatherOpenMap.Sys sys = new WeatherOpenMap.Sys();
        sys.setSunrise(1696140000L); // 2023-10-01 06:00:00 UTC
        sys.setSunset(1696183200L); // 2023-10-01 18:00:00 UTC
        source.setSys(sys);

        // Act
        Weather result = mapper.toWeather(source);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getWeatherId()).isEqualTo(800);
        assertThat(result.getWeatherMain()).isEqualTo("Clear");
        assertThat(result.getWeatherDescription()).isEqualTo("clear sky");
        assertThat(result.getWeatherIcon()).isEqualTo("01d");

        assertThat(result.getMainTemperature()).isEqualTo(21); // Rounded 20.5
        assertThat(result.getMainFeelsLike()).isEqualTo(20); // Rounded 19.8
        assertThat(result.getMainTempMin()).isEqualTo(18); // Rounded 18.2
        assertThat(result.getMainTempMax()).isEqualTo(22); // Rounded 22.1
        assertThat(result.getMainPressure()).isEqualTo(1013);
        assertThat(result.getMainHumidity()).isEqualTo(55);

        assertThat(result.getTimezone()).isEqualTo(3600);

        // Verify timestamps (checking not null and roughly correct is usually enough
        // for timezone dependent tests,
        // but here we can check against system default zone as implemented in mapper)
        assertThat(result.getTimestamp()).isNotNull();
        assertThat(result.getSunrise()).isNotNull();
        assertThat(result.getSunset()).isNotNull();
    }

    @Test
    void toWeather_shouldReturnNull_whenSourceIsNull() {
        assertThat(mapper.toWeather(null)).isNull();
    }

    @Test
    void toWeather_shouldHandleEmptyWeatherList() {
        WeatherOpenMap source = new WeatherOpenMap();
        source.setWeather(List.of());

        Weather result = mapper.toWeather(source);

        assertThat(result).isNotNull();
        assertThat(result.getWeatherId()).isZero();
        assertThat(result.getWeatherMain()).isNull();
    }
}
