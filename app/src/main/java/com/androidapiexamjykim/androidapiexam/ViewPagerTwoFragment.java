package com.androidapiexamjykim.androidapiexam;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.androidapiexamjykim.androidapiexam.Model2.List;
import com.androidapiexamjykim.androidapiexam.Model2.Weather;

import java.util.ArrayList;

/**
 * Created by user on 2017-03-23.
 */

public class ViewPagerTwoFragment extends Fragment {

    private ExpandableListView mListView;
    private ListViewAdapter mAdepter;
    private ArrayList<List> mWeatherData;

    public static ViewPagerTwoFragment newInstance(ArrayList<List> mWeatherData) {
        ViewPagerTwoFragment viewPagerTwoFragment = new ViewPagerTwoFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("weather", mWeatherData);
        viewPagerTwoFragment.setArguments(bundle);
        return viewPagerTwoFragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_viewpager_two, container, false);

        Bundle bundle = getArguments();
        mWeatherData = (ArrayList<List>) bundle.getSerializable("weather");
        mListView = (ExpandableListView) view.findViewById(R.id.expand_list);
        mAdepter = new ListViewAdapter(mWeatherData);
        mListView.setAdapter(mAdepter);

        return view;
    }
}

class ListViewAdapter extends BaseExpandableListAdapter {

    private ArrayList<List> mGroupData;

    public ListViewAdapter(ArrayList<List> groupData) {
        this.mGroupData = groupData;
    }

    @Override
    public int getGroupCount() {
        return mGroupData.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mGroupData.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group, parent, false);
            viewHolder.group = (TextView) convertView.findViewById(R.id.group_text);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String data = (String) getGroup(groupPosition);
        viewHolder.group.setText(data);


        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return 1;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weather, parent, false);
            viewHolder.humidity = (TextView) convertView.findViewById(R.id.humidity_item);
            viewHolder.pressure = (TextView) convertView.findViewById(R.id.pressure_item);
            viewHolder.temp = (TextView) convertView.findViewById(R.id.temp_item);
            viewHolder.weather = (TextView) convertView.findViewById(R.id.weather_item);
            viewHolder.windDag = (TextView) convertView.findViewById(R.id.winddag_item);
            viewHolder.windSpeed = (TextView) convertView.findViewById(R.id.windspeed_item);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.humidity.setText(mGroupData.get(groupPosition).getMain().getHumidity());
        viewHolder.pressure.setText(mGroupData.get(groupPosition).getMain().getPressure());
        viewHolder.temp.setText(mGroupData.get(groupPosition).getMain().getTemp());
        viewHolder.weather.setText(mGroupData.get(groupPosition).getWeather().toString());
        viewHolder.windDag.setText(mGroupData.get(groupPosition).getWind().getDeg());
        viewHolder.windSpeed.setText(mGroupData.get(groupPosition).getWind().getSpeed());



        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class ViewHolder {
        TextView group;
        TextView weather;
        TextView temp;
        TextView windSpeed;
        TextView windDag;
        TextView pressure;
        TextView humidity;
    }
}
