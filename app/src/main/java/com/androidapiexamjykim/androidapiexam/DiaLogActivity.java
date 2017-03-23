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
                    ViewPagerOneFragment fragment = ViewPagerOneFragment
                            .newInstance((WeatherModel) getIntent()
                                    .getSerializableExtra("data"));
                    return fragment;

                case 1:
                    System.out.println("");

            }
            return null;
        }

        @Override
        public int getCount() {
            return 1;
        }
    }
}
