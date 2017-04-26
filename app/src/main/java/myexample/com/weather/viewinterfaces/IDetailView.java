package myexample.com.weather.viewinterfaces;

import myexample.com.weather.network.json.CityDetail;

public interface IDetailView extends IBaseView {
    void onCityDetailsReceived(CityDetail cityDetail);
    void onCityDetailError(Throwable t);
}
