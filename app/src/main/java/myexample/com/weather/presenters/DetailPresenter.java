package myexample.com.weather.presenters;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import myexample.com.weather.MyApplication;
import myexample.com.weather.network.json.CityDetail;
import myexample.com.weather.network.service.WeatherService;
import myexample.com.weather.viewinterfaces.IDetailView;


public class DetailPresenter extends BasePresenter<IDetailView> {
    private static String TAG = "DetailPresenter";
    @Inject
    WeatherService weatherService;

    public DetailPresenter(IDetailView v) {
        super(v);
        MyApplication.getApplicationComponent().inject(this);
    }

    public void getDetailedWeather(String cityName) {
        cityName = cityName.replaceAll("\\s+",""); //remove all spaces

        weatherService.getForcastForCity(cityName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CityDetail>() {
                    @Override
                    public void accept(@NonNull CityDetail cityDetail) throws Exception {

                        final IDetailView detailView = getStrongViewReference();
                        if (detailView != null) {
                            detailView.onCityDetailsReceived(cityDetail);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        final IDetailView detailView = getStrongViewReference();
                        if (detailView != null) {
                            detailView.onCityDetailError(throwable);
                        }
                    }
                });
    }

}
