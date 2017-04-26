
package myexample.com.weather.network.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SevenDay {

    @SerializedName("Monday")
    @Expose
    public DayOfWeek monday;
    @SerializedName("Tuesday")
    @Expose
    public DayOfWeek tuesday;
    @SerializedName("Friday")
    @Expose
    public DayOfWeek friday;
    @SerializedName("Wednesday")
    @Expose
    public DayOfWeek wednesday;
    @SerializedName("Thursday")
    @Expose
    public DayOfWeek thursday;
    @SerializedName("Sunday")
    @Expose
    public DayOfWeek sunday;
    @SerializedName("Saturday")
    @Expose
    public DayOfWeek saturday;

}
