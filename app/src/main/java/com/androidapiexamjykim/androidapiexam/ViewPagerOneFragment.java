package com.androidapiexamjykim.androidapiexam;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidapiexamjykim.androidapiexam.Model.WeatherModel;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by user on 2017-03-13.
 */

public class ViewPagerOneFragment extends Fragment {

    private TextView mSunriseText;
    private TextView mSunsetText;
    private TextView mWindText;
    private TextView mWeatherText;
    private TextView mTemperatureText;
    private TextView mPressureText;
    private TextView mHumidityText;
    private TextView mVisibilityText;

    private WeatherModel mWeatherData;

    public static ViewPagerOneFragment newInstance(WeatherModel mWeatherData) {
        ViewPagerOneFragment viewPagerOneFragment = new ViewPagerOneFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("weather", mWeatherData);
        viewPagerOneFragment.setArguments(bundle);
        return viewPagerOneFragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_viewpager_one, container, false);

        mSunriseText = (TextView) view.findViewById(R.id.sunrise_text);
        mSunsetText = (TextView) view.findViewById(R.id.sunset_text);
        mWindText = (TextView) view.findViewById(R.id.wind_text);
        mWeatherText = (TextView) view.findViewById(R.id.weather_text);
        mTemperatureText = (TextView) view.findViewById(R.id.temperature_text);
        mPressureText = (TextView) view.findViewById(R.id.pressure_text);
        mHumidityText = (TextView) view.findViewById(R.id.humidity_text);
        mVisibilityText = (TextView) view.findViewById(R.id.visibility_text);

        mWeatherData = (WeatherModel) getArguments().getSerializable("weather");
        // 일출
        SimpleDateFormat sunRise = new SimpleDateFormat("hh:mm", Locale.KOREA);
        sunRise.setTimeZone(TimeZone.getTimeZone("UTC"));

        // 일몰
        SimpleDateFormat sunSet = new SimpleDateFormat("kk:mm", Locale.KOREA);
        sunSet.setTimeZone(TimeZone.getTimeZone("UTC"));

        mSunriseText.setText(sunRise.format(mWeatherData.getSys().getSunrise()));
        mSunsetText.setText(sunSet.format(mWeatherData.getSys().getSunset()));
        mWindText.setText(mWeatherData.getWind().getSpeed());
        mWeatherText.setText(mWeatherData.getWeather().toString());
        mTemperatureText.setText(mWeatherData.getMain().getTemp());
        mPressureText.setText(mWeatherData.getMain().getPressure());
        mHumidityText.setText(mWeatherData.getMain().getHumidity());
        mVisibilityText.setText(mWeatherData.getVisibility());

        return view;
    }
}

