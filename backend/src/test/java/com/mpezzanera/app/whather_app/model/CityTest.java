package com.mpezzanera.app.whather_app.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

@JsonTest
class CityTest {

    @Autowired
    private JacksonTester<City> json;

    @Test
    void testDeserialize() throws Exception {
        String jsonContent = """
                {
                    "name": "London",
                    "local_names": {
                        "en": "London",
                        "it": "Londra"
                    },
                    "lat": 51.5085,
                    "lon": -0.1257,
                    "country": "GB"
                }
                """;

        City city = this.json.parse(jsonContent).getObject();

        assertThat(city.getName()).isEqualTo("London");
        assertThat(city.getLocales()).containsEntry("en", "London");
        assertThat(city.getLocales()).containsEntry("it", "Londra");
    }
}
