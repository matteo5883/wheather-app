export interface Weather {
  weatherId: number;
  weatherMain: string;
  weatherDescription: string;
  weatherIcon: string;
  mainTemperature: number;
  mainFeelsLike: number;
  mainTempMin: number;
  mainTempMax: number;
  mainPressure: number;
  mainHumidity: number;
  timezone: number;
  timestamp: string;
  sunrise: string;
  sunset: string;
}
