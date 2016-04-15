/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abohawa;

/**
 *
 * @author rana
 */
public final class WeatherData {

    public final long dt;
    public final Coord coord;
    public final Weather weather[];
    public final String name;
    public final long cod;
    public final Main main;
    public final Clouds clouds;
    public final long id;
    public final Sys sys;
    public final String base;
    public final Wind wind;

    public WeatherData(long dt, Coord coord, Weather[] weather, String name, long cod, Main main, Clouds clouds, long id, Sys sys, String base, Wind wind) {
        this.dt = dt;
        this.coord = coord;
        this.weather = weather;
        this.name = name;
        this.cod = cod;
        this.main = main;
        this.clouds = clouds;
        this.id = id;
        this.sys = sys;
        this.base = base;
        this.wind = wind;
    }

    public static final class Coord {

        public final double lon;
        public final double lat;

        public Coord(double lon, double lat) {
            this.lon = lon;
            this.lat = lat;
        }
    }

    public static final class Weather {

        public final String icon;
        public final String description;
        public final String main;
        public final long id;

        public Weather(String icon, String description, String main, long id) {
            this.icon = icon;
            this.description = description;
            this.main = main;
            this.id = id;
        }
    }

    public static final class Main {

        public final double temp;
        public final double temp_min;
        public final double grnd_level;
        public final long humidity;
        public final double pressure;
        public final double sea_level;
        public final double temp_max;

        public Main(double temp, double temp_min, double grnd_level, long humidity, double pressure, double sea_level, double temp_max) {
            this.temp = temp;
            this.temp_min = temp_min;
            this.grnd_level = grnd_level;
            this.humidity = humidity;
            this.pressure = pressure;
            this.sea_level = sea_level;
            this.temp_max = temp_max;
        }
    }

    public static final class Clouds {

        public final long all;

        public Clouds(long all) {
            this.all = all;
        }
    }

    public static final class Sys {

        public final String country;
        public final long sunrise;
        public final long sunset;
        public final double message;

        public Sys(String country, long sunrise, long sunset, double message) {
            this.country = country;
            this.sunrise = sunrise;
            this.sunset = sunset;
            this.message = message;
        }
    }

    public static final class Wind {

        public final long deg;
        public final double speed;

        public Wind(long deg, double speed) {
            this.deg = deg;
            this.speed = speed;
        }
    }
}
