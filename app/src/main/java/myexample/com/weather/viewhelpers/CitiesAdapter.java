package myexample.com.weather.viewhelpers;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import myexample.com.weather.R;
import myexample.com.weather.databinding.CityHeaderBinding;
import myexample.com.weather.databinding.CountryHeaderBinding;
import myexample.com.weather.network.json.City;
import myexample.com.weather.network.json.CityList;
import myexample.com.weather.network.json.Country;
import myexample.com.weather.util.WeatherFormatUtils;
import myexample.com.weather.util.WeatherToIconMap;

public class CitiesAdapter extends RecyclerView.Adapter<BindingViewHolder<?>> {
    public static int COUNTRY_TYPE = 0;
    public static int ITEM_TYPE = 1;
    private List<Object> cityAndCountryList;
    ICityItemClickHandler cityItemClickHandler;


    public CitiesAdapter(@NonNull CityList list, @NonNull ICityItemClickHandler cityItemClickHandler) {
        this.cityItemClickHandler = cityItemClickHandler;

        final Map<String, List<City>> countryToCityList = new HashMap<>();
        for (City city : list.cities) {
            final List<City> cityList;
            if (!countryToCityList.containsKey(city.country)) {
                cityList = new ArrayList<>();
                countryToCityList.put(city.country, cityList);
            } else {
                cityList = countryToCityList.get(city.country);
            }
            cityList.add(city);
        }

        cityAndCountryList = sort(countryToCityList);
    }

    private List<Object> sort(Map<String, List<City>> countryToCityList) {
        final Comparator<City> cityComparator = new Comparator<City>() {

            @Override
            public int compare(City o1, City o2) {
                if (o1 == o2) return 0;
                return o1.name.compareTo(o2.name);
            }

        };

        //sort all the countries only
        final List<String> sortedCountries = new ArrayList<>();
        sortedCountries.addAll(countryToCityList.keySet());
        Collections.sort(sortedCountries);

        //for each country sort the given cities in alpha order
        for (String key : sortedCountries) {
            final List<City> cities = countryToCityList.get(key);
            Collections.sort(cities, cityComparator);
        }

        List<Object> cityCountryList = new ArrayList<>();
        //go through both countries and cities and 'flatten' the list into a list of both
        //countries and cities
        for (String key : sortedCountries) {
            final List<City> cities = countryToCityList.get(key);
            if (cities.size() == 0) continue;
            final Country country = new Country();
            country.name = cities.get(0).country;
            cityCountryList.add(country);
            //Add all cities
            for (City city : cities) {
                cityCountryList.add(city);
            }
        }
        return cityCountryList;
    }
    @Override
    public BindingViewHolder<?> onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        //cityAndCountryList.get()
        final ViewDataBinding binding;
        if (viewType == COUNTRY_TYPE) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.country_header, viewGroup, false);
        } else {
            binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.city_header, viewGroup, false);
        }
        return new BindingViewHolder<>(binding);
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<?> viewHolder, int position) {
        if (viewHolder.getBinding() instanceof CountryHeaderBinding) {
            final CountryHeaderBinding countryHeaderBinding = (CountryHeaderBinding) viewHolder.getBinding();
            final Country country = (Country) cityAndCountryList.get(position);
            countryHeaderBinding.headerText.setText(country.name);
        } else {
            final CityHeaderBinding cityHeaderBinding = (CityHeaderBinding) viewHolder.getBinding();
            final City city = (City) cityAndCountryList.get(position);
            cityHeaderBinding.city.setText(city.name);
            cityHeaderBinding.degrees.setText(WeatherFormatUtils.getDegreesFromNumber(city.temp, true));
            cityHeaderBinding.weatherIcon.setImageResource(WeatherToIconMap.getResourceForWeather(city.shortDescription));
            cityHeaderBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cityItemClickHandler.cityClicked(city, cityHeaderBinding);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return cityAndCountryList != null ? cityAndCountryList.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return cityAndCountryList.get(position) instanceof Country ? COUNTRY_TYPE : ITEM_TYPE;
    }

}
