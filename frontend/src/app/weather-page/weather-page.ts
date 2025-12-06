import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { DatePipe } from '@angular/common';
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
  imports: [FormsModule, DatePipe],
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
  showSearchModal: boolean = false;
  selectedCity: City | null = null;
  searchLoading: boolean = false;
  searchError: string | null = null;

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
   * Open search modal
   */
  openSearchModal(): void {
    this.showSearchModal = true;
    this.searchQuery = '';
    this.cities = [];
    this.selectedCity = null;
    this.searchError = null;
  }

  /**
   * Close search modal
   */
  closeSearchModal(): void {
    this.showSearchModal = false;
    this.searchQuery = '';
    this.cities = [];
    this.selectedCity = null;
    this.searchError = null;
  }

  /**
   * Search cities by name (triggered by button)
   */
  searchCities(): void {
    if (!this.searchQuery || this.searchQuery.trim().length < 2) {
      this.searchError = 'Please enter at least 2 characters';
      return;
    }

    this.searchLoading = true;
    this.searchError = null;

    this.apiService.getCities(this.searchQuery.trim()).subscribe({
      next: (cities) => {
        this.searchLoading = false;
        this.cities = cities;
        if (cities.length === 0) {
          this.searchError = 'No cities found. Try another search.';
        }
      },
      error: (err) => {
        this.searchLoading = false;
        this.searchError = 'Error searching cities. Please try again.';
        console.error('Search error:', err);
      },
    });
  }

  /**
   * Select a city from search results
   */
  onCitySelect(city: City): void {
    this.selectedCity = city;
  }

  /**
   * Confirm city selection and fetch weather
   */
  confirmCitySelection(): void {
    if (!this.selectedCity) {
      return;
    }

    this.currentLocation = {
      lat: this.selectedCity.lat,
      lon: this.selectedCity.lon,
      cityName: `${this.selectedCity.name}, ${this.selectedCity.country}`,
    };
    this.saveLocation(this.currentLocation);
    this.closeSearchModal();
    this.fetchWeather(this.selectedCity.lat, this.selectedCity.lon);
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
        this.loading = false;
        this.weather = weather;
      },
      error: (err) => {
        console.error('Weather error:', err);
        this.loading = false;
        this.error = 'Error fetching weather data. Please try again.';
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
