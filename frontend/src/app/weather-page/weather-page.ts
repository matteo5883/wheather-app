import { Component, OnInit, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { DatePipe } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatDialogModule, MatDialog } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatListModule } from '@angular/material/list';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatDividerModule } from '@angular/material/divider';
import { MatChipsModule } from '@angular/material/chips';
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
  imports: [
    FormsModule,
    DatePipe,
    MatButtonModule,
    MatCardModule,
    MatIconModule,
    MatProgressSpinnerModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    MatListModule,
    MatToolbarModule,
    MatDividerModule,
    MatChipsModule,
  ],
  templateUrl: './weather-page.html',
  styleUrl: './weather-page.scss',
})
export class WeatherPage implements OnInit {
  weather = signal<Weather | null>(null);
  cities = signal<City[]>([]);
  searchQuery = signal<string>('');
  loading = signal<boolean>(false);
  error = signal<string | null>(null);
  locationEnabled = signal<boolean>(false);
  currentLocation = signal<SavedLocation | null>(null);
  showSearchModal = signal<boolean>(false);
  selectedCity = signal<City | null>(null);
  searchLoading = signal<boolean>(false);
  searchError = signal<string | null>(null);

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
        const location = JSON.parse(saved);
        this.currentLocation.set(location);
        this.fetchWeather(location.lat, location.lon);
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
      this.loading.set(true);
      this.error.set(null);
      navigator.geolocation.getCurrentPosition(
        (position) => {
          const lat = position.coords.latitude;
          const lon = position.coords.longitude;
          this.locationEnabled.set(true);
          const location = {
            lat,
            lon,
            cityName: 'Current Location',
          };
          this.currentLocation.set(location);
          this.saveLocation(location);
          this.fetchWeather(lat, lon);
        },
        (error) => {
          this.loading.set(false);
          this.error.set('Unable to retrieve your location. Please search for a city.');
          console.error('Geolocation error:', error);
        }
      );
    } else {
      this.loading.set(false);
      this.error.set('Geolocation is not supported by your browser.');
    }
  }

  /**
   * Open search modal
   */
  openSearchModal(): void {
    this.showSearchModal.set(true);
    this.searchQuery.set('');
    this.cities.set([]);
    this.selectedCity.set(null);
    this.searchError.set(null);
  }

  /**
   * Close search modal
   */
  closeSearchModal(): void {
    this.showSearchModal.set(false);
    this.searchQuery.set('');
    this.cities.set([]);
    this.selectedCity.set(null);
    this.searchError.set(null);
  }

  /**
   * Search cities by name (triggered by button)
   */
  searchCities(): void {
    const query = this.searchQuery();
    if (!query || query.trim().length < 2) {
      this.searchError.set('Please enter at least 2 characters');
      return;
    }

    this.searchLoading.set(true);
    this.searchError.set(null);

    this.apiService.getCities(query.trim()).subscribe({
      next: (cities) => {
        this.searchLoading.set(false);
        this.cities.set(cities);
        if (cities.length === 0) {
          this.searchError.set('No cities found. Try another search.');
        }
      },
      error: (err) => {
        this.searchLoading.set(false);
        this.searchError.set('Error searching cities. Please try again.');
        console.error('Search error:', err);
      },
    });
  }

  /**
   * Select a city from search results
   */
  onCitySelect(city: City): void {
    this.selectedCity.set(city);
  }

  /**
   * Confirm city selection and fetch weather
   */
  confirmCitySelection(): void {
    const city = this.selectedCity();
    if (!city) {
      return;
    }

    const location = {
      lat: city.lat,
      lon: city.lon,
      cityName: `${city.name}, ${city.country}`,
    };
    this.currentLocation.set(location);
    this.saveLocation(location);
    this.closeSearchModal();
    this.fetchWeather(city.lat, city.lon);
  }

  /**
   * Fetch weather data for given coordinates
   */
  private fetchWeather(lat: number, lon: number): void {
    this.loading.set(true);
    this.error.set(null);

    console.log('Fetching weather for:', lat, lon);

    this.apiService.getWeather(lat, lon).subscribe({
      next: (weather) => {
        console.log('Weather data received:', weather);
        this.loading.set(false);
        this.weather.set(weather);
      },
      error: (err) => {
        console.error('Weather error:', err);
        this.loading.set(false);
        this.error.set('Error fetching weather data. Please try again.');
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
