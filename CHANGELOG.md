# Changelog

All notable changes to this project will be documented in this file.

## [Unreleased]

### Added

- Initialized changelog file.

### Added

- Implemented `OpenWeatherMapServiceImpl` using Spring `RestClient` for external API calls.
- Externalized API configuration properties (`openweathermap.api.key`, `openweathermap.api.url`).
- Updated `application.properties` to read `openweathermap.api.key` from the environment variable `OPENWEATHER_API_KEY` instead of a hardcoded value.
- Implemented `CityController` with `GET /api/cities` endpoint.
- Added Swagger/OpenAPI documentation (`springdoc-openapi-starter-webmvc-ui`).
- Added `CityControllerTest` using `@WebMvcTest` and `@MockitoBean`.
- Added `SecurityConfig` to permit all requests to `/api/**` and Swagger UI endpoints, disabling authentication for development.
- Added `spring-boot-devtools` dependency to enable automatic application restart on code changes.
- Implemented `WeatherOpenMap` model based on OpenWeatherMap API response structure.
- Added `WeatherOpenMapTest` to verify JSON deserialization.
- Added `Weather` model class to return a subset of the data returned by the OpenWeatherMapApi and added `WeatherTest` to verify JSON serialization and deserialization for the `Weather` model.
- Implemented `WeatherMapper` to convert `WeatherOpenMap` to `Weather` domain model.
- Added `WeatherMapperTest` to verify mapping logic.
- Added missing file `application.properties` in test folder

### Removed

- Removed `spring-boot-starter-data-jpa` dependency to prevent unwanted datasource auto-configuration.

### Fixed

- Added `@Configuration` annotation to `RestClientConfig` to properly expose the `RestClient` bean.
- Updated `OpenWeatherMapServiceImpl` to include the `appid` query parameter, leveraging the default URI variable configured in `RestClientConfig`.

### Changed

- Updated `City` model to map `local_names` JSON field to `locales` map using `@JsonProperty`.
- Changed `locales` type from `List<String>` to `Map<String, String>` to correctly represent the data structure.
- Added `CityTest` to verify JSON deserialization.
- Updated `City` model field name from `countryCode` to `state` to reflect value returned by the API
