package com.androidapiexamjykim.androidapiexam;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.androidapiexamjykim.androidapiexam.Model.WeatherModel;
import com.androidapiexamjykim.androidapiexam.Model2.Forecast;
import com.androidapiexamjykim.androidapiexam.Model2.List;

import java.util.ArrayList;

public class DiaLogActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dia_log);

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mTabLayout = (TabLayout) findViewById(R.id.tap);

        CustomViewPagerAdapter adapter = new CustomViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);

        mTabLayout.setupWithViewPager(mViewPager);

    }

    public void onClick(View v) {
        this.finish();
    }

    private class CustomViewPagerAdapter extends FragmentPagerAdapter {

        public CustomViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    final ViewPagerOneFragment fragment = ViewPagerOneFragment
                            .newInstance((WeatherModel) getIntent()
                                    .getSerializableExtra("data"));
                    return fragment;

                case 1:
                    Forecast mForecast = (Forecast) getIntent().getSerializableExtra("data2");
                    final ViewPagerTwoFragment fragment1 = ViewPagerTwoFragment
                            .newInstance((ArrayList<List>) mForecast.getList());
                    return fragment1;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0 :
                    String weatherData = "현재 날씨";
                    return weatherData;

                case 1 :
                    String forecast = "날씨 예보";
                    return forecast;
            }
            return null;
        }
    }
}
