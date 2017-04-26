
package myexample.com.weather.network.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Wind {
    @SerializedName("direction")
    @Expose
    public String direction;
    @SerializedName("speed")
    @Expose
    public Double speed;
}
