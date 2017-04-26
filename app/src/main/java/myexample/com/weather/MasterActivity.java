package myexample.com.weather;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import myexample.com.weather.databinding.CityHeaderBinding;
import myexample.com.weather.databinding.MasterActivityBinding;
import myexample.com.weather.network.json.City;
import myexample.com.weather.network.json.CityList;
import myexample.com.weather.presenters.MasterPresenter;
import myexample.com.weather.util.WeatherFormatUtils;
import myexample.com.weather.util.WeatherToIconMap;
import myexample.com.weather.viewhelpers.CitiesAdapter;
import myexample.com.weather.viewhelpers.ICityItemClickHandler;
import myexample.com.weather.viewinterfaces.IMasterView;

public class MasterActivity extends BaseActivity<MasterPresenter> implements IMasterView, ICityItemClickHandler {

    private static String TAG = "MasterActivity";

    private MasterActivityBinding binding;

    @Override
    protected MasterPresenter createPresenter() {
        return new MasterPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.master_activity) ;
        setSupportActionBar(binding.myToolbar);
        final ActionBar ab = getSupportActionBar();
        //TODO: localize hard coded strings
        ab.setTitle("City Weather");
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(MasterActivity.this));
        binding.recyclerView.setFocusable(false);
        binding.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPresenter().getWeather();
            }
        });
        //initially set the refreshing state until we populate the recyclerview
        binding.refreshLayout.setRefreshing(true);
        getPresenter().getWeather();
    }

    @Override
    public void onCityListReceived(CityList cityList) {
        binding.recyclerView.setAdapter(new CitiesAdapter(cityList, this));
        binding.refreshLayout.setRefreshing(false);
    }

    @Override
    public void onCityListError(Throwable t) {
        Log.e(TAG, t.toString());
        //TODO: replace with snackbar
        Toast.makeText(MasterActivity.this, "Unable to get list of cities", Toast.LENGTH_LONG).show();
        binding.refreshLayout.setRefreshing(false);
    }


    @Override
    public void cityClicked(City city, CityHeaderBinding binding) {
        final Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_CITY, city.name);
        intent.putExtra(DetailActivity.EXTRA_IMAGE_RESOURCE, WeatherToIconMap.getResourceForWeather(city.shortDescription));
        intent.putExtra(DetailActivity.EXTRA_DEGREES, WeatherFormatUtils.getDegreesFromNumber(city.temp, true));

        final Pair<View, String> p1 = Pair.create((View) binding.city, "title");
        final Pair<View, String> p2 = Pair.create((View) binding.weatherIcon, "icon");
        final Pair<View, String> p3 = Pair.create((View) binding.degrees, "degrees");
        final ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, p1, p2, p3);
        this.startActivity(intent, options.toBundle());
    }
}







