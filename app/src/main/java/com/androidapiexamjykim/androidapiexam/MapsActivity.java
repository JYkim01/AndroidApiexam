package com.androidapiexamjykim.androidapiexam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.androidapiexamjykim.androidapiexam.Helper.RetrofitUtil;
import com.androidapiexamjykim.androidapiexam.Model.WeatherModel;
import com.androidapiexamjykim.androidapiexam.Model2.WeatherModel2;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

    private GoogleMap mGoogleMap;
    private Api mApi;
    private Call<WeatherModel> mCall;
    private Call<WeatherModel2> mCall2;
    private WeatherModel result;
    private WeatherModel2 result2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mApi = new RetrofitUtil().getUserApi();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mGoogleMap = googleMap;
        mGoogleMap.setOnMapLongClickListener(this);
    }

    @Override
    public void onMapLongClick(final LatLng latLng) {
        mCall = mApi.getJson(Api.API_KEY, Double.parseDouble(String.valueOf(latLng.latitude)), Double.parseDouble(String.valueOf(latLng.longitude)));
        mCall.enqueue(new Callback<WeatherModel>() {
            @Override
            public void onResponse(Call<WeatherModel> call, Response<WeatherModel> response) {
                // 정상 결과
                result = response.body();

                // 일출
                SimpleDateFormat sunRise = new SimpleDateFormat("hh:mm", Locale.KOREA);
                sunRise.setTimeZone(TimeZone.getTimeZone("UTC"));

                // 일몰
                SimpleDateFormat sunSet = new SimpleDateFormat("kk:mm", Locale.KOREA);
                sunSet.setTimeZone(TimeZone.getTimeZone("UTC"));

                Marker marker = mGoogleMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title(sunRise.format(result.getSys().getSunrise() * 1000L)
                                + "," + sunSet.format(result.getSys().getSunset() * 1000L)));

                marker.showInfoWindow();
                marker.hideInfoWindow();

                mCall2 = mApi.getJson2(Api.API_KEY,Double.parseDouble(String.valueOf(latLng.latitude)),Double.parseDouble(String.valueOf(latLng.longitude)));
                mCall2.enqueue(new Callback<WeatherModel2>() {
                    @Override
                    public void onResponse(Call<WeatherModel2> call, Response<WeatherModel2> response) {
                        result2 = response.body();

                        GoogleMap.OnInfoWindowClickListener onInfoWindowClickListener = new GoogleMap.OnInfoWindowClickListener() {
                            @Override
                            public void onInfoWindowClick(Marker marker) {
                                Intent intent = new Intent(MapsActivity.this, DiaLogActivity.class);
                                intent.putExtra("data", result);
                                intent.putExtra("data2", result2);
                                startActivity(intent);
                            }
                        };
                        mGoogleMap.setOnInfoWindowClickListener(onInfoWindowClickListener);
                    }

                    @Override
                    public void onFailure(Call<WeatherModel2> call, Throwable t) {
                        Toast.makeText(MapsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(Call<WeatherModel> call, Throwable t) {
                Toast.makeText(MapsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}