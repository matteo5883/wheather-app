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

### Removed

- Removed `spring-boot-starter-data-jpa` dependency to prevent unwanted datasource auto-configuration.

### Fixed

- Added `@Configuration` annotation to `RestClientConfig` to properly expose the `RestClient` bean.
- Updated `OpenWeatherMapServiceImpl` to include the `appid` query parameter, leveraging the default URI variable configured in `RestClientConfig`.

### Changed

- Updated `City` model to map `local_names` JSON field to `locales` map using `@JsonProperty`.
- Changed `locales` type from `List<String>` to `Map<String, String>` to correctly represent the data structure.
- Added `CityTest` to verify JSON deserialization.
