
package myexample.com.weather.network.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CityDetail {

    @SerializedName("airPressure")
    @Expose
    public String airPressure;
    @SerializedName("sevenDay")
    @Expose
    public SevenDay sevenDay;
    @SerializedName("rainChance")
    @Expose
    public Integer rainChance;
    @SerializedName("humidity")
    @Expose
    public Integer humidity;
    @SerializedName("longDescription")
    @Expose
    public String longDescription;
    @SerializedName("feelsLike")
    @Expose
    public Double feelsLike;
    @SerializedName("wind")
    @Expose
    public Wind wind;

}
