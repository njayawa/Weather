package myexample.com.weather.dagger;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import myexample.com.weather.network.service.WeatherService;


@Module
public class AppModule {

    Application mApplication;

    public AppModule(Application application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    public WeatherService providesWeatherService() {
        return WeatherService.WeatherServiceFactory.create();
    }
}
