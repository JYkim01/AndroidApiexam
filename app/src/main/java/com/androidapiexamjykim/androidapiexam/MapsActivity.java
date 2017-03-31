package com.androidapiexamjykim.androidapiexam;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.androidapiexamjykim.androidapiexam.Helper.RetrofitUtil;
import com.androidapiexamjykim.androidapiexam.Model.WeatherModel;
import com.androidapiexamjykim.androidapiexam.Model2.Forecast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.androidapiexamjykim.androidapiexam.Api.API_KEY;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

    private GoogleMap mGoogleMap;
    private Api mApi;
    private Call<WeatherModel> mCall;
    private Call<Forecast> mCall2;
    private WeatherModel result;
    private Forecast result2;
    private double mLat;
    private double mLon;
    private Marker mMarker;
    private Geocoder mGeocoder;
    private List<Address> mAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mApi = new RetrofitUtil().getUserApi();
        mGeocoder = new Geocoder(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                search(query);

                if (mAddress.size() == 0) {
                    Toast.makeText(MapsActivity.this, "입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    double lat = mAddress.get(0).getLatitude();
                    double lon = mAddress.get(0).getLongitude();

                    LatLng latLng = new LatLng(lat, lon);
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));

                    apiData(lat, lon);

                    InputMethodManager hide = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    hide.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    private void search(String city) {
        try {
            mAddress = mGeocoder.getFromLocationName(city, 10);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (mAddress != null) {
            if (mAddress.size() == 0) {
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mGoogleMap = googleMap;
        mGoogleMap.setOnMapLongClickListener(this);

        if (result == null) {
            LatLng startingPoint = new LatLng(37.56, 126.97);
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(startingPoint, 10));
        } else {

            double lat = result.getCoord().getLat();
            double lon = result.getCoord().getLon();

            LatLng latLng = new LatLng(lat, lon);

            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));

            // 일출
            SimpleDateFormat sunRise = new SimpleDateFormat("hh:mm", Locale.KOREA);
            sunRise.setTimeZone(TimeZone.getTimeZone("UTC"));

            // 일몰
            SimpleDateFormat sunSet = new SimpleDateFormat("kk:mm", Locale.KOREA);
            sunSet.setTimeZone(TimeZone.getTimeZone("UTC"));

            mMarker = mGoogleMap.addMarker(new MarkerOptions().position(latLng)
                    .title("" + sunRise.format(result.getSys().getSunrise() * 1000L)
                            + "→" + sunSet.format(result.getSys().getSunset() * 1000L)));

            mMarker.showInfoWindow();
            mMarker.hideInfoWindow();

            mCall2 = mApi.getJson2(API_KEY, lat, lon);
            mCall2.enqueue(new Callback<Forecast>() {
                @Override
                public void onResponse(Call<Forecast> call, Response<Forecast> response) {
                    result2 = response.body();

                    GoogleMap.OnInfoWindowClickListener infoWindowClickListener = new GoogleMap.OnInfoWindowClickListener() {
                        @Override
                        public void onInfoWindowClick(Marker marker) {
                            Intent intent = new Intent(MapsActivity.this, DiaLogActivity.class);
                            intent.putExtra("data", result);
                            intent.putExtra("forecast", result2);

                            startActivity(intent);
                        }
                    };
                    mGoogleMap.setOnInfoWindowClickListener(infoWindowClickListener);
                }

                @Override
                public void onFailure(Call<Forecast> call, Throwable t) {
                    Toast.makeText(MapsActivity.this, "실패", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onMapLongClick(final LatLng latLng) {
        mLat = latLng.latitude;
        mLon = latLng.longitude;

        apiData(mLat, mLon);
    }

    private void apiData(final double lat, final double lon) {

        final LatLng latLng = new LatLng(lat, lon);

        mCall = mApi.getJson(API_KEY, lat, lon);
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

                if (mMarker != null) {
                    Marker marker = mGoogleMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .title(sunRise.format(result.getSys().getSunrise() * 1000L)
                                    + "," + sunSet.format(result.getSys().getSunset() * 1000L)));

                    marker.showInfoWindow();
                    marker.hideInfoWindow();

                    mCall2 = mApi.getJson2(API_KEY, lat, lon);
                    mCall2.enqueue(new Callback<Forecast>() {
                        @Override
                        public void onResponse(Call<Forecast> call, Response<Forecast> response) {
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
                        public void onFailure(Call<Forecast> call, Throwable t) {
                            Toast.makeText(MapsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    mMarker = mGoogleMap.addMarker(new MarkerOptions().position(latLng)
                            .title("" + sunRise.format(result.getSys().getSunrise() * 1000L)
                                    + "→" + sunSet.format(result.getSys().getSunset() * 1000L)));

                    mMarker.showInfoWindow();
                    mMarker.hideInfoWindow();

                    mCall2 = mApi.getJson2(API_KEY, lat, lon);
                    mCall2.enqueue(new Callback<Forecast>() {
                        @Override
                        public void onResponse(Call<Forecast> call, Response<Forecast> response) {
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
                        public void onFailure(Call<Forecast> call, Throwable t) {
                            Toast.makeText(MapsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<WeatherModel> call, Throwable t) {
                Toast.makeText(MapsActivity.this, "실패", Toast.LENGTH_SHORT).show();
            }
        });
    }
}