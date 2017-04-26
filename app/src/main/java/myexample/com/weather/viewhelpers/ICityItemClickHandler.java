package myexample.com.weather.viewhelpers;

import myexample.com.weather.databinding.CityHeaderBinding;
import myexample.com.weather.network.json.City;

public interface ICityItemClickHandler {
    void cityClicked(City city, CityHeaderBinding binding);
}
