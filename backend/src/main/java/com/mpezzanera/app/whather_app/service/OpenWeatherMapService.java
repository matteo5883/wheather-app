package com.mpezzanera.app.whather_app.service;

import java.util.List;

import com.mpezzanera.app.whather_app.model.City;

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

    /*
     * https://api.openweathermap.org/data/3.0/onecall/timemachine?lat={lat}&lon={
     * lon}&dt={time}&appid={API key}
     * Parameters
     * lat required Latitude, decimal (-90; 90). If you need the geocoder to
     * automatic convert city names and zip-codes to geo coordinates and the other
     * way around, please use our Geocoding API
     * lon required Longitude, decimal (-180; 180). If you need the geocoder to
     * automatic convert city names and zip-codes to geo coordinates and the other
     * way around, please use our Geocoding API
     * dt required Timestamp (Unix time, UTC time zone), e.g. dt=1586468027. Data is
     * available from January 1st, 1979 till 4 days ahead
     * appid required Your unique API key (you can always find it on your account
     * page under the "API key" tab)
     * units optional Units of measurement. standard, metric and imperial units are
     * available. If you do not use the units parameter, standard units will be
     * applied by default. Learn more
     * lang optional You can use the lang parameter to get the output in your
     * language. Learn more
     */

    /*
     * https://api.openweathermap.org/data/3.0/onecall/day_summary?lat={lat}&lon={
     * lon}&date={date}&appid={API key}
     * Parameters
     * lat required Latitude, decimal (-90; 90)
     * lon required Longitude, decimal (-180; 180)
     * date required Date in the `YYYY-MM-DD` format for which data is requested.
     * Date is available for 46+ years archive (starting from 1979-01-02) up to the
     * 1,5 years ahead forecast to the current date
     * appid required Your unique API key (you can always find it on your account
     * page under the "API key" tab)
     * units optional Units of measurement. standard, metric and imperial units are
     * available. If you do not use the units parameter, standard units will be
     * applied by default. Learn more
     * lang optional You can use the lang parameter to get the output in your
     * language. Learn more
     */

    /*
     * https://api.openweathermap.org/data/3.0/onecall/overview?lat={lat}&lon={lon}&
     * appid={API key}
     * Parameters
     * lat required Latitude, decimal (-90; 90)
     * lon required Longitude, decimal (-180; 180)
     * appid required Your unique API key (you can always find it on your account
     * page under the "API key" tab)
     * date optional The date the user wants to get a weather summary in the
     * YYYY-MM-DD format. Data is available for today and tomorrow. If not
     * specified, the current date will be used by default. Please note that the
     * date is determined by the timezone relevant to the coordinates specified in
     * the API request
     * units optional Units of measurement. Standard, metric and imperial units are
     * available. If you do not use the units parameter, standard units will be
     * applied by default. Learn more
     */

}