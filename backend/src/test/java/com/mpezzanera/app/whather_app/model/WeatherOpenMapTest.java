package com.mpezzanera.app.whather_app.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

@JsonTest
class WeatherOpenMapTest {

    @Autowired
    private JacksonTester<WeatherOpenMap> json;

    @Test
    void testDeserialize() throws Exception {
        String jsonContent = """
                {
                    "coord": {
                        "lon": 9.1896,
                        "lat": 45.4642
                    },
                    "weather": [
                        {
                            "id": 803,
                            "main": "Clouds",
                            "description": "broken clouds",
                            "icon": "04d"
                        }
                    ],
                    "base": "stations",
                    "main": {
                        "temp": 7.73,
                        "feels_like": 6.49,
                        "temp_min": 6.61,
                        "temp_max": 10,
                        "pressure": 1015,
                        "humidity": 72,
                        "sea_level": 1015,
                        "grnd_level": 1001
                    },
                    "visibility": 10000,
                    "wind": {
                        "speed": 2.06,
                        "deg": 260
                    },
                    "clouds": {
                        "all": 75
                    },
                    "dt": 1764506186,
                    "sys": {
                        "type": 2,
                        "id": 2107179,
                        "country": "IT",
                        "sunrise": 1764484910,
                        "sunset": 1764517345
                    },
                    "timezone": 3600,
                    "id": 3173435,
                    "name": "Milan",
                    "cod": 200
                }
                """;

        WeatherOpenMap weather = this.json.parse(jsonContent).getObject();

        assertThat(weather.getName()).isEqualTo("Milan");
        assertThat(weather.getCoord().getLon()).isEqualTo(9.1896);
        assertThat(weather.getMain().getFeelsLike()).isEqualTo(6.49);
        assertThat(weather.getWeather()).hasSize(1);
        assertThat(weather.getWeather().get(0).getMain()).isEqualTo("Clouds");
        assertThat(weather.getSys().getCountry()).isEqualTo("IT");
    }
}
