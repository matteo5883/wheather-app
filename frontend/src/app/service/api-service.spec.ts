import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { ApiService } from './api-service';
import { City } from '../models/city.model';
import { Weather } from '../models/weather.model';

describe('ApiService', () => {
  let service: ApiService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    service = TestBed.inject(ApiService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should get cities by name', () => {
    const mockCities: City[] = [
      {
        name: 'London',
        country: 'GB',
        lat: 51.5085,
        lon: -0.1257,
      },
    ];

    service.getCities('London', undefined, 'GB', 5).subscribe((cities) => {
      expect(cities).toEqual(mockCities);
      expect(cities.length).toBe(1);
      expect(cities[0].name).toBe('London');
    });

    const req = httpMock.expectOne(
      'http://localhost:8080/api/v1/cities?name=London&limit=5&country=GB'
    );
    expect(req.request.method).toBe('GET');
    req.flush(mockCities);
  });

  it('should get weather by coordinates', () => {
    const mockWeather: Weather = {
      weatherId: 800,
      weatherMain: 'Clear',
      weatherDescription: 'clear sky',
      weatherIcon: '01d',
      mainTemperature: 20,
      mainFeelsLike: 18,
      mainTempMin: 15,
      mainTempMax: 25,
      mainPressure: 1012,
      mainHumidity: 60,
      timezone: 3600,
      timestamp: '2023-10-01T12:00:00',
      sunrise: '2023-10-01T06:00:00',
      sunset: '2023-10-01T18:00:00',
    };

    service.getWeather(45.4642, 9.19, 'metric', 'en').subscribe((weather) => {
      expect(weather).toEqual(mockWeather);
      expect(weather.weatherMain).toBe('Clear');
      expect(weather.mainTemperature).toBe(20);
    });

    const req = httpMock.expectOne(
      'http://localhost:8080/api/v1/weather?lat=45.4642&lon=9.19&units=metric&lang=en'
    );
    expect(req.request.method).toBe('GET');
    req.flush(mockWeather);
  });
});
