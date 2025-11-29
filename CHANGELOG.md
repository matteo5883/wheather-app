# Changelog

All notable changes to this project will be documented in this file.

## [Unreleased]

### Added

- Initialized changelog file.

### Added

- Implemented `OpenWeatherMapServiceImpl` using Spring `RestClient` for external API calls.
- Externalized API configuration properties (`openweathermap.api.key`, `openweathermap.api.url`).
- Updated `application.properties` to read `openweathermap.api.key` from the environment variable `OPENWEATHER_API_KEY` instead of a hardcoded value.
