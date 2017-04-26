package myexample.com.weather.util;

import myexample.com.weather.R;

public class WeatherToIconMap {
    public static int getResourceForWeather(String weather) {
        switch (weather.toLowerCase()) {
            case "drizzle" :
                return R.drawable.drizzle;
            case "sunny":
                return R.drawable.sunny;
            case "cloudy":
                return R.drawable.cloudy;
            case "overcast":
                return R.drawable.partly_cloudy;
            case "storms":
                return R.drawable.thunderstorms;
            case "raining":
                return R.drawable.hail;
            case "windy":
                return R.drawable.windy;
            default:
                return R.drawable.unknown;

        }
    }
}
