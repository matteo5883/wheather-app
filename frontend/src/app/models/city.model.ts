export interface City {
  name: string;
  country: string;
  state?: string;
  lat: number;
  lon: number;
  locales?: { [key: string]: string };
}
