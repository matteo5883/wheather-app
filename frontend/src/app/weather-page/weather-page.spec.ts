import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { of, throwError } from 'rxjs';
import { describe, it, expect, beforeEach, vi } from 'vitest';

import { WeatherPage } from './weather-page';
import { ApiService } from '../service/api-service';
import { City } from '../models/city.model';
import { Weather } from '../models/weather.model';

describe('WeatherPage', () => {
  let component: WeatherPage;
  let fixture: ComponentFixture<WeatherPage>;
  let apiServiceMock: any;

  const mockCity: City = {
    name: 'London',
    lat: 51.5074,
    lon: -0.1278,
    country: 'GB',
    state: 'England',
  };

  const mockWeather: Weather = {
    weatherId: 800,
    weatherMain: 'Clear',
    weatherDescription: 'clear sky',
    weatherIcon: '01d',
    mainTemperature: 20,
    mainFeelsLike: 18,
    mainTempMin: 17,
    mainTempMax: 22,
    mainPressure: 1013,
    mainHumidity: 65,
    timezone: 0,
    timestamp: '2024-01-15T12:00:00',
    sunrise: '2024-01-15T07:30:00',
    sunset: '2024-01-15T17:45:00',
  };

  beforeEach(async () => {
    apiServiceMock = {
      getCities: vi.fn(),
      getWeather: vi.fn(),
    };

    // Mock localStorage
    const localStorageMock = {
      getItem: vi.fn(),
      setItem: vi.fn(),
      removeItem: vi.fn(),
      clear: vi.fn(),
      key: vi.fn(),
      length: 0,
    };
    Object.defineProperty(window, 'localStorage', {
      value: localStorageMock,
      writable: true,
    });

    // Mock geolocation
    const geolocationMock = {
      getCurrentPosition: vi.fn(),
    };
    Object.defineProperty(navigator, 'geolocation', {
      value: geolocationMock,
      writable: true,
      configurable: true,
    });

    await TestBed.configureTestingModule({
      imports: [WeatherPage],
      providers: [
        provideHttpClient(),
        provideHttpClientTesting(),
        { provide: ApiService, useValue: apiServiceMock },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(WeatherPage);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize with default values', () => {
    expect(component.weather).toBeNull();
    expect(component.cities).toEqual([]);
    expect(component.searchQuery).toBe('');
    expect(component.loading).toBe(false);
    expect(component.error).toBeNull();
    expect(component.locationEnabled).toBe(false);
    expect(component.currentLocation).toBeNull();
  });

  describe('requestLocation', () => {
    it('should get current position and fetch weather', async () => {
      const mockPosition = {
        coords: {
          latitude: 51.5074,
          longitude: -0.1278,
        },
      } as GeolocationPosition;

      (navigator.geolocation.getCurrentPosition as any).mockImplementation(
        (success: PositionCallback) => {
          success(mockPosition);
        }
      );

      apiServiceMock.getWeather.mockReturnValue(of(mockWeather));

      component.requestLocation();

      await new Promise((resolve) => setTimeout(resolve, 100));

      expect(component.locationEnabled).toBe(true);
      expect(component.currentLocation).toEqual({
        cityName: 'Current Location',
        lat: 51.5074,
        lon: -0.1278,
      });
      expect(apiServiceMock.getWeather).toHaveBeenCalledWith(51.5074, -0.1278);
    });

    it('should handle geolocation error', async () => {
      const mockError = {
        code: 1,
        message: 'User denied geolocation',
      } as GeolocationPositionError;

      (navigator.geolocation.getCurrentPosition as any).mockImplementation(
        (success: PositionCallback, error: PositionErrorCallback) => {
          error(mockError);
        }
      );

      component.requestLocation();

      await new Promise((resolve) => setTimeout(resolve, 100));

      expect(component.error).toBe('Unable to retrieve your location. Please search for a city.');
      expect(component.loading).toBe(false);
    });
  });

  describe('searchCities', () => {
    it('should search cities when query length >= 2', async () => {
      component.searchQuery = 'London';
      apiServiceMock.getCities.mockReturnValue(of([mockCity]));

      component.searchCities();

      await new Promise((resolve) => setTimeout(resolve, 100));

      expect(apiServiceMock.getCities).toHaveBeenCalledWith('London');
      expect(component.cities).toEqual([mockCity]);
    });

    it('should clear cities when query length < 2', () => {
      component.searchQuery = 'L';
      component.cities = [mockCity];

      component.searchCities();

      expect(component.cities).toEqual([]);
      expect(apiServiceMock.getCities).not.toHaveBeenCalled();
    });

    it('should handle search error', async () => {
      component.searchQuery = 'London';
      apiServiceMock.getCities.mockReturnValue(throwError(() => new Error('API error')));

      component.searchCities();

      await new Promise((resolve) => setTimeout(resolve, 100));

      expect(component.error).toBe('Error searching cities. Please try again.');
    });
  });

  describe('selectCity', () => {
    it('should select city and fetch weather', () => {
      apiServiceMock.getWeather.mockReturnValue(of(mockWeather));

      component.selectCity(mockCity);

      expect(component.currentLocation).toEqual({
        cityName: 'London, GB',
        lat: 51.5074,
        lon: -0.1278,
      });
      expect(apiServiceMock.getWeather).toHaveBeenCalledWith(51.5074, -0.1278);
      expect(component.cities).toEqual([]);
      expect(component.searchQuery).toBe('');
    });
  });
});
