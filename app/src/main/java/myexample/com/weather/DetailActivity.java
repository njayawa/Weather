package myexample.com.weather;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.widget.Toast;

import myexample.com.weather.databinding.DetailActivityBinding;
import myexample.com.weather.databinding.ForecastDayBinding;
import myexample.com.weather.network.json.CityDetail;
import myexample.com.weather.network.json.DayOfWeek;
import myexample.com.weather.presenters.DetailPresenter;
import myexample.com.weather.util.WeatherFormatUtils;
import myexample.com.weather.util.WeatherToIconMap;
import myexample.com.weather.viewinterfaces.IDetailView;

public class DetailActivity extends BaseActivity<DetailPresenter> implements IDetailView {

    public static String EXTRA_CITY = "extra_city";
    public static String EXTRA_IMAGE_RESOURCE = "image_resource";
    public static String EXTRA_DEGREES = "extra_degrees";

    private DetailActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.detail_activity);
        setSupportActionBar(binding.myToolbar);
        final ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
        final Bundle extras = this.getIntent().getExtras();
        final String city;
        if (extras != null) {
            city = extras.getString(EXTRA_CITY);
            binding.city.setText(city);
            binding.weatherIcon.setImageResource(extras.getInt(EXTRA_IMAGE_RESOURCE));
            binding.degrees.setText(extras.getString(EXTRA_DEGREES));
            ab.setTitle(city + " Weather"); //TODO: localize all of these
        } else {
            city = "";
        }
        binding.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPresenter().getDetailedWeather(city);
            }
        });
        binding.refreshLayout.setRefreshing(true);
        getPresenter().getDetailedWeather(city);
    }

    //This is required for shared element transitions when user hits back on actionbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return(super.onOptionsItemSelected(item));
    }

    @Override
    protected DetailPresenter createPresenter() {
        return new DetailPresenter(this);
    }

    @Override
    public void onCityDetailsReceived(CityDetail cityDetail) {
        //TODO: localize
        binding.feelsLike.setText("Feels like " + WeatherFormatUtils.getDegreesFromNumber(cityDetail.feelsLike, true));
        binding.chanceOfRain.setText("Chance of rain: " + cityDetail.rainChance + "%");
        binding.humidity.setText(cityDetail.humidity + "% humidity");
        setDayOfWeekItems(binding.sunday, cityDetail.sevenDay.sunday, "Sunday");
        setDayOfWeekItems(binding.monday, cityDetail.sevenDay.monday, "Monday");
        setDayOfWeekItems(binding.tuesday, cityDetail.sevenDay.tuesday, "Tuesday");
        setDayOfWeekItems(binding.wednesday, cityDetail.sevenDay.wednesday, "Wednesday");
        setDayOfWeekItems(binding.thursday, cityDetail.sevenDay.thursday, "Thursday");
        setDayOfWeekItems(binding.friday, cityDetail.sevenDay.friday, "Friday");
        setDayOfWeekItems(binding.saturday, cityDetail.sevenDay.saturday, "Saturday");
        binding.refreshLayout.setRefreshing(false);
    }

    private void setDayOfWeekItems(ForecastDayBinding binding, DayOfWeek dayOfWeek, String dayOfWeekLiteral) {
        binding.dayName.setText(dayOfWeekLiteral);
        binding.dayIcon.setImageResource(WeatherToIconMap.getResourceForWeather(dayOfWeek.shortDescription));
        binding.dayDegreesLow.setText(WeatherFormatUtils.getDegreesFromNumber(dayOfWeek.minTemp, true));
        binding.dayDegreesHigh.setText(WeatherFormatUtils.getDegreesFromNumber(dayOfWeek.maxTemp, true));

    }

    @Override
    public void onCityDetailError(Throwable t) {
        Toast.makeText(this, "Unable to get list of cities", Toast.LENGTH_LONG).show();
        binding.refreshLayout.setRefreshing(false);
    }
}
