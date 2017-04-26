package myexample.com.weather.util;

public class WeatherFormatUtils {
    public static String getDegreesFromNumber(double d, boolean isCelcius) {
        String returnVal = "" + Math.round(d) + "°";
        returnVal += isCelcius ? "C" : "F";
        return returnVal;
    }
}
