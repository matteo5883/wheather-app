import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { City } from '../models/city.model';
import { Weather } from '../models/weather.model';

@Injectable({
  providedIn: 'root',
})
export class ApiService {
  private readonly apiUrl = 'http://localhost:8080/api/v1';

  constructor(private http: HttpClient) {}

  /**
   * Search cities by name
   * @param name City name (required)
   * @param state State code (optional)
   * @param country Country code (optional)
   * @param limit Limit the number of results (default 5)
   * @returns Observable of City array
   */
  getCities(
    name: string,
    state?: string,
    country?: string,
    limit: number = 5
  ): Observable<City[]> {
    let params = new HttpParams()
      .set('name', name)
      .set('limit', limit.toString());

    if (state) {
      params = params.set('state', state);
    }
    if (country) {
      params = params.set('country', country);
    }

    return this.http.get<City[]>(`${this.apiUrl}/cities`, { params });
  }

  /**
   * Get weather by coordinates
   * @param lat Latitude (required)
   * @param lon Longitude (required)
   * @param units Units of measurement (optional, default: metric)
   * @param lang Language code (optional, default: en)
   * @returns Observable of Weather object
   */
  getWeather(
    lat: number,
    lon: number,
    units: string = 'metric',
    lang: string = 'en'
  ): Observable<Weather> {
    const params = new HttpParams()
      .set('lat', lat.toString())
      .set('lon', lon.toString())
      .set('units', units)
      .set('lang', lang);

    return this.http.get<Weather>(`${this.apiUrl}/weather`, { params });
  }
}
