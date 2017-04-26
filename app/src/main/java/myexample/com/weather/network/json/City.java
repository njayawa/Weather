
package myexample.com.weather.network.json;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class City {


    @SerializedName("maxTemp")
    @Expose
    public Double maxTemp;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("temp")
    @Expose
    public Double temp;
    @SerializedName("country")
    @Expose
    public String country;
    @SerializedName("long")
    @Expose
    public Double _long;
    @SerializedName("minTemp")
    @Expose
    public Double minTemp;
    @SerializedName("lat")
    @Expose
    public Double lat;
    @SerializedName("shortDescription")
    @Expose
    public String shortDescription;

}
