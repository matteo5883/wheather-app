# Changelog

All notable changes to this project will be documented in this file.

## [0.0.1] - 2025-12-06

### Backend

#### Added

- Initialized changelog file.

### Added

- Implemented `OpenWeatherMapServiceImpl` using Spring `RestClient` for external API calls.
- Externalized API configuration properties (`openweathermap.api.key`, `openweathermap.api.url`).
- Updated `application.properties` to read `openweathermap.api.key` from the environment variable `OPENWEATHER_API_KEY` instead of a hardcoded value.
- Implemented `CityController` with `GET /api/cities` endpoint.
- Added Swagger/OpenAPI documentation (`springdoc-openapi-starter-webmvc-ui`).
- Added `CityControllerTest` using `@WebMvcTest` and `@MockitoBean`.
- Added `SecurityConfig` to permit all requests to `/api/**` and Swagger UI endpoints, disabling authentication for development.
- Configured CORS in `SecurityConfig` to allow requests from frontend (http://localhost:4200).
- Disabled CSRF protection for API endpoints to enable frontend-backend communication.
- Added `spring-boot-devtools` dependency to enable automatic application restart on code changes.
- Implemented `WeatherOpenMap` model based on OpenWeatherMap API response structure.
- Added `WeatherOpenMapTest` to verify JSON deserialization.
- Added `Weather` model class to return a subset of the data returned by the OpenWeatherMapApi and added `WeatherTest` to verify JSON serialization and deserialization for the `Weather` model.
- Implemented `WeatherMapper` to convert `WeatherOpenMap` to `Weather` domain model.
- Added `WeatherMapperTest` to verify mapping logic.
- Added missing file `application.properties` in test folder
- Added method `getWeather` to class `OpenWeatherMapService` and relative test.
- Implemented `WeatherController` with `GET /api/v1/weather` endpoint to retrieve weather data by coordinates.
- Added `WeatherControllerTest` to verify weather endpoint functionality.
- Added Swagger documentation for weather endpoint.
- Added API versioning (v1) to all endpoints (`/api/v1/cities`, `/api/v1/weather`).
- Set application version to 0.0.1.

### Frontend

#### Added

- Implemented `ApiService` with methods to call backend endpoints.
- Added `getCities()` method to search cities by name.
- Added `getWeather()` method to retrieve weather data by coordinates.
- Created `City` and `Weather` TypeScript models.
- Added `provideHttpClient` to application configuration.
- Added comprehensive unit tests for `ApiService`.
- Set frontend application version to 0.0.1.
- Implemented `WeatherPage` component to display weather data.
- Added geolocation support to request user's current location.
- Implemented localStorage persistence for selected location.
- Added city search functionality with autocomplete results.
- Created weather display with temperature, description, humidity, pressure, and sunrise/sunset times.
- Added responsive styling with gradient background for weather page.
- Implemented loading states and error handling for all async operations.
- Added comprehensive unit tests for `WeatherPage` component.
- Configured routing to display `WeatherPage` as the main page (root route).
- Simplified `app.html` to use only `<router-outlet />` for route rendering.
- Implemented modal search functionality for city selection.
- Added search button to trigger city search instead of real-time search.
- Created modal popup with search input, results list, and confirmation buttons.
- Added city selection with visual feedback (highlighted selected city).
- Implemented OK/Cancel buttons in modal for user confirmation.
- Added separate loading and error states for search modal.
- Enhanced modal styling with animations and responsive design.

### Removed

- Removed `spring-boot-starter-data-jpa` dependency to prevent unwanted datasource auto-configuration.

### Fixed

- Added `@Configuration` annotation to `RestClientConfig` to properly expose the `RestClient` bean.
- Updated `OpenWeatherMapServiceImpl` to include the `appid` query parameter, leveraging the default URI variable configured in `RestClientConfig`.
- Fixed loading indicator not disappearing when geolocation is not supported or disabled.
- Removed automatic geolocation request on localStorage parse error to prevent infinite loading.
- Added console logging for weather fetch operations to aid debugging.

### Changed

- Updated `City` model to map `local_names` JSON field to `locales` map using `@JsonProperty`.
- Changed `locales` type from `List<String>` to `Map<String, String>` to correctly represent the data structure.
- Added `CityTest` to verify JSON deserialization.
- Updated `City` model field name from `countryCode` to `state` to reflect value returned by the API
- Migrated template syntax from `*ngIf` and `*ngFor` to new Angular control flow syntax (`@if`, `@for`).
- Updated `weather-page.html` to use modern Angular 17+ block syntax for better performance and readability.
- **Migrated all component properties to Angular Signals for reactive state management**.
- Converted all boolean, object, and array properties to signals with automatic change detection.
- Updated template bindings to use signal syntax (calling signals as functions).
- Replaced `[(ngModel)]` with `[ngModel]` and `(ngModelChange)` for signal compatibility.
- Improved reactivity and performance with signals-based architecture.
- Updated all unit tests in `weather-page.spec.ts` to work with signals.
- Modified test assertions to call signals as functions (e.g., `component.loading()` instead of `component.loading`).
- Added tests for modal operations (open, close, city selection, confirmation).
- Updated signal mutations in tests to use `.set()` method.
- Fixed `app.spec.ts` test to verify router-outlet presence instead of non-existent h1 element.
- **Integrated Angular Material throughout the application**.
- Replaced custom UI components with Material components (toolbar, cards, buttons, icons, form fields, lists, chips).
- Added Material modules: `MatButtonModule`, `MatCardModule`, `MatIconModule`, `MatProgressSpinnerModule`, `MatDialogModule`, `MatFormFieldModule`, `MatInputModule`, `MatListModule`, `MatToolbarModule`, `MatDividerModule`, `MatChipsModule`.
- Reduced custom CSS from ~570 lines to ~330 lines by leveraging Material's built-in styling.
- Implemented hover animations on all interactive elements (buttons, cards, chips, icons).
- Made application fully responsive with no white borders - occupies full viewport.
- Updated `styles.scss` to remove margins/padding and enable full-screen layout.
- Fixed Angular compiler warning by wrapping `@if/@else` button content in `<ng-container>` tags.
