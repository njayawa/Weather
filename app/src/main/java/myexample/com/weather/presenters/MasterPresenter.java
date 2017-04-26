package myexample.com.weather.presenters;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import myexample.com.weather.MyApplication;
import myexample.com.weather.network.json.CityList;
import myexample.com.weather.network.service.WeatherService;
import myexample.com.weather.viewinterfaces.IMasterView;


public class MasterPresenter extends BasePresenter<IMasterView> {
    @Inject
    WeatherService weatherService;

    public MasterPresenter(IMasterView v) {
        super(v);
        //Ideally, we wanto abstract MyApplication out of presenters
        MyApplication.getApplicationComponent().inject(this);
    }

    public void getWeather() {
        weatherService.getWeather()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CityList>() {
                    @Override
                    public void accept(@NonNull CityList cityList) throws Exception {
                        final IMasterView masterView = getStrongViewReference();
                        if (masterView != null) {
                            masterView.onCityListReceived(cityList);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        final IMasterView masterView = getStrongViewReference();
                        if (masterView != null) {
                            masterView.onCityListError(throwable);
                        }
                    }
                });
    }

}
