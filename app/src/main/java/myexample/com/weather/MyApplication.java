package myexample.com.weather;

import android.app.Application;

import myexample.com.weather.dagger.AppComponent;
import myexample.com.weather.dagger.AppModule;
import myexample.com.weather.dagger.DaggerAppComponent;

public class MyApplication extends Application {

    private static AppComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeInjector();
    }

    private void initializeInjector() {
        applicationComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public static AppComponent getApplicationComponent() {
        return applicationComponent;
    }


}