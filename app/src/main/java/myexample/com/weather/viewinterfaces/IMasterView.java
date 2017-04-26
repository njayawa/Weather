package myexample.com.weather.viewinterfaces;

import myexample.com.weather.network.json.CityList;

public interface IMasterView extends IBaseView {
    void onCityListReceived(CityList cityList);
    void onCityListError(Throwable t);
}
