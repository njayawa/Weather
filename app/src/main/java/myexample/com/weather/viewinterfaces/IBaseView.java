package myexample.com.weather.viewinterfaces;

import myexample.com.weather.dagger.AppComponent;

public interface IBaseView {
    boolean isViewAttached();
    AppComponent getAppCompnent();
}
