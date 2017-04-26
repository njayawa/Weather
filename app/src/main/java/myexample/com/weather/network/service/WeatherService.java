package myexample.com.weather.network.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.reactivex.Observable;
import myexample.com.weather.network.json.CityDetail;
import myexample.com.weather.network.json.CityList;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {
    @GET("/weather")
    Observable<CityList> getWeather();

    @GET("/detailed-forecast")
    Observable<CityDetail> getForcastForCity(@Query("city") String city);

     class WeatherServiceFactory {
        public static final String BASE_URL = "http://localhost:8080/";
        public static WeatherService create() {
            Gson gson = new GsonBuilder().create();
            GsonConverterFactory converterFactory = GsonConverterFactory.create(gson);
            return  new Retrofit.Builder()
                    .addConverterFactory(converterFactory)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(BASE_URL)
                    .build()
                    .create(WeatherService.class);

        }
    }
}
