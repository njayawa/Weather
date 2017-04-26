
package myexample.com.weather.network.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DayOfWeek {
    @SerializedName("minTemp")
    @Expose
    public Double minTemp;
    @SerializedName("maxTemp")
    @Expose
    public Double maxTemp;
    @SerializedName("shortDescription")
    @Expose
    public String shortDescription;
}
