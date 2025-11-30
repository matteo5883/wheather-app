package com.mpezzanera.app.whather_app.service;

import java.util.List;

import com.mpezzanera.app.whather_app.model.City;
import com.mpezzanera.app.whather_app.model.Weather;

public interface OpenWeatherMapService {

    /*
     * http://api.openweathermap.org/geo/1.0/direct?q={city name},{state
     * code},{country code}&limit={limit}&appid={API key}
     * Parameters
     * q required City name, state code (only for the US) and country code divided
     * by comma. Please use ISO 3166 country codes.
     * appid required Your unique API key (you can always find it on your account
     * page under the "API key" tab)
     * limit optional Number of the locations in the API response (up to 5 results
     * can be returned in the API response)
     */
    List<City> getCityByName(String cityName, String stateCode, String countryCode, int limit);

    /**
     * https://api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={API
     * key}
     * Parameters
     * lat required Latitude. If you need the geocoder to automatic convert city
     * names and zip-codes to geo coordinates and the other way around, please use
     * our Geocoding API
     * lon required Longitude. If you need the geocoder to automatic convert city
     * names and zip-codes to geo coordinates and the other way around, please use
     * our Geocoding API
     * appid required Your unique API key (you can always find it on your account
     * page under the "API key" tab)
     * mode optional Response format. Possible values are xml and html. If you don't
     * use the mode parameter format is JSON by default. Learn more
     * units optional Units of measurement. standard, metric and imperial units are
     * available. If you do not use the units parameter, standard units will be
     * applied by default.
     * Learn more
     * lang optional You can use this parameter to get the output in your language.
     * Learn more
     */
    Weather getWeather(double lat, double lon, String units, String lang);
}