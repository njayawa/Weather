package myexample.com.weather.presenters;

import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;

import myexample.com.weather.viewinterfaces.IBaseView;

public class BasePresenter<T extends IBaseView> {
    private WeakReference<T> viewReference;

    public BasePresenter(@NonNull T view) {
        this.viewReference = new WeakReference<T>(view);
    }

    public void setView(T value) {
        this.viewReference = new WeakReference<T>(value);
    }
    public WeakReference<T> getView() {return viewReference;}

    public T getStrongViewReference() {
        T value = getView().get();
        if (value != null && value.isViewAttached()) {
            return value;
        }
        return null;
    }
}
