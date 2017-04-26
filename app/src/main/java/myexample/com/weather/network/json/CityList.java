
package myexample.com.weather.network.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CityList {

    @SerializedName("cities")
    @Expose
    public List<City> cities = null;

}
