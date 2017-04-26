package myexample.com.weather.dagger;

import javax.inject.Singleton;

import dagger.Component;
import myexample.com.weather.DetailActivity;
import myexample.com.weather.MasterActivity;
import myexample.com.weather.presenters.DetailPresenter;
import myexample.com.weather.presenters.MasterPresenter;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    void inject(MasterActivity activity);
    void inject(DetailActivity activity);
    void inject(DetailPresenter presenter);
    void inject(MasterPresenter presenter);
}