package com.mpezzanera.app.whather_app.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

@JsonTest
class WeatherTest {

    @Autowired
    private JacksonTester<Weather> json;

    @Test
    void testSerialize() throws Exception {
        Weather weather = new Weather();
        weather.setWeatherId(800);
        weather.setWeatherMain("Clear");
        weather.setWeatherDescription("clear sky");
        weather.setWeatherIcon("01d");
        weather.setMainTemperature(20);
        weather.setMainFeelsLike(18);
        weather.setMainTempMin(15);
        weather.setMainTempMax(25);
        weather.setMainPressure(1012);
        weather.setMainHumidity(60);
        weather.setTimezone(3600);
        
        LocalDateTime now = LocalDateTime.of(2023, 10, 1, 12, 0, 0);
        weather.setTimestamp(now);
        weather.setSunrise(now.minusHours(6));
        weather.setSunset(now.plusHours(6));

        JsonContent<Weather> result = this.json.write(weather);

        assertThat(result).extractingJsonPathNumberValue("$.weatherId").isEqualTo(800);
        assertThat(result).extractingJsonPathStringValue("$.weatherMain").isEqualTo("Clear");
        assertThat(result).extractingJsonPathStringValue("$.weatherDescription").isEqualTo("clear sky");
        assertThat(result).extractingJsonPathStringValue("$.weatherIcon").isEqualTo("01d");
        assertThat(result).extractingJsonPathNumberValue("$.mainTemperature").isEqualTo(20);
        assertThat(result).extractingJsonPathNumberValue("$.mainFeelsLike").isEqualTo(18);
        assertThat(result).extractingJsonPathNumberValue("$.mainTempMin").isEqualTo(15);
        assertThat(result).extractingJsonPathNumberValue("$.mainTempMax").isEqualTo(25);
        assertThat(result).extractingJsonPathNumberValue("$.mainPressure").isEqualTo(1012);
        assertThat(result).extractingJsonPathNumberValue("$.mainHumidity").isEqualTo(60);
        assertThat(result).extractingJsonPathNumberValue("$.timezone").isEqualTo(3600);
        assertThat(result).extractingJsonPathStringValue("$.timestamp").isEqualTo("2023-10-01T12:00:00");
    }

    @Test
    void testDeserialize() throws Exception {
        String jsonContent = """
                {
                    "weatherId": 800,
                    "weatherMain": "Clear",
                    "weatherDescription": "clear sky",
                    "weatherIcon": "01d",
                    "mainTemperature": 20,
                    "mainFeelsLike": 18,
                    "mainTempMin": 15,
                    "mainTempMax": 25,
                    "mainPressure": 1012,
                    "mainHumidity": 60,
                    "timezone": 3600,
                    "timestamp": "2023-10-01T12:00:00",
                    "sunrise": "2023-10-01T06:00:00",
                    "sunset": "2023-10-01T18:00:00"
                }
                """;

        Weather weather = this.json.parse(jsonContent).getObject();

        assertThat(weather.getWeatherId()).isEqualTo(800);
        assertThat(weather.getWeatherMain()).isEqualTo("Clear");
        assertThat(weather.getMainTemperature()).isEqualTo(20);
        assertThat(weather.getTimestamp()).isEqualTo(LocalDateTime.of(2023, 10, 1, 12, 0, 0));
    }
}
