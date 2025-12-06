import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ApiService } from '../service/api-service';
import { City } from '../models/city.model';
import { Weather } from '../models/weather.model';

interface SavedLocation {
  lat: number;
  lon: number;
  cityName: string;
}

@Component({
  selector: 'app-weather-page',
  imports: [CommonModule, FormsModule],
  templateUrl: './weather-page.html',
  styleUrl: './weather-page.scss',
})
export class WeatherPage implements OnInit {
  weather: Weather | null = null;
  cities: City[] = [];
  searchQuery: string = '';
  loading: boolean = false;
  error: string | null = null;
  locationEnabled: boolean = false;
  currentLocation: SavedLocation | null = null;

  constructor(private apiService: ApiService) {}

  ngOnInit(): void {
    this.loadSavedLocation();
  }

  /**
   * Load saved location from localStorage and fetch weather
   */
  private loadSavedLocation(): void {
    const saved = localStorage.getItem('selectedLocation');
    if (saved) {
      try {
        this.currentLocation = JSON.parse(saved);
        this.fetchWeather(this.currentLocation!.lat, this.currentLocation!.lon);
      } catch (e) {
        console.error('Error parsing saved location', e);
        // Don't auto-request location on parse error
      }
    }
  }

  /**
   * Request user's geolocation
   */
  requestLocation(): void {
    if (navigator.geolocation) {
      this.loading = true;
      this.error = null;
      navigator.geolocation.getCurrentPosition(
        (position) => {
          const lat = position.coords.latitude;
          const lon = position.coords.longitude;
          this.locationEnabled = true;
          this.currentLocation = {
            lat,
            lon,
            cityName: 'Current Location',
          };
          this.saveLocation(this.currentLocation);
          this.fetchWeather(lat, lon);
        },
        (error) => {
          this.loading = false;
          this.error = 'Unable to retrieve your location. Please search for a city.';
          console.error('Geolocation error:', error);
        }
      );
    } else {
      this.loading = false;
      this.error = 'Geolocation is not supported by your browser.';
    }
  }

  /**
   * Search cities by name
   */
  searchCities(): void {
    if (!this.searchQuery || this.searchQuery.trim().length < 2) {
      this.cities = [];
      return;
    }

    this.loading = true;
    this.error = null;

    this.apiService.getCities(this.searchQuery.trim()).subscribe({
      next: (cities) => {
        this.cities = cities;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Error searching cities. Please try again.';
        this.loading = false;
        console.error('Search error:', err);
      },
    });
  }

  /**
   * Select a city and fetch its weather
   */
  selectCity(city: City): void {
    this.currentLocation = {
      lat: city.lat,
      lon: city.lon,
      cityName: `${city.name}, ${city.country}`,
    };
    this.saveLocation(this.currentLocation);
    this.fetchWeather(city.lat, city.lon);
    this.cities = [];
    this.searchQuery = '';
  }

  /**
   * Fetch weather data for given coordinates
   */
  private fetchWeather(lat: number, lon: number): void {
    this.loading = true;
    this.error = null;

    console.log('Fetching weather for:', lat, lon);

    this.apiService.getWeather(lat, lon).subscribe({
      next: (weather) => {
        console.log('Weather data received:', weather);
        this.weather = weather;
        this.loading = false;
      },
      error: (err) => {
        console.error('Weather error:', err);
        this.error = 'Error fetching weather data. Please try again.';
        this.loading = false;
      },
    });
  }

  /**
   * Save location to localStorage
   */
  private saveLocation(location: SavedLocation): void {
    localStorage.setItem('selectedLocation', JSON.stringify(location));
  }
}
