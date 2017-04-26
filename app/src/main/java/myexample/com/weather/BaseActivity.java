package myexample.com.weather;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import myexample.com.weather.viewinterfaces.IBaseView;
import myexample.com.weather.dagger.AppComponent;
import myexample.com.weather.presenters.BasePresenter;

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements IBaseView {
    private T presenter;
    public AppComponent getAppComponent() {
        return ((MyApplication) getApplication()).getApplicationComponent();
    }
    public void setPresenter(T presenter) {
        this.presenter = presenter;
    }



    public T getPresenter() {
        return presenter;
    }

    @Override
    public boolean isViewAttached() {
        return this.isFinishing() ? false : true;
    }

    @Override
    public AppComponent getAppCompnent() {
        return ((MyApplication) getApplication()).getApplicationComponent();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter(createPresenter());
    }

    abstract protected T createPresenter();
}