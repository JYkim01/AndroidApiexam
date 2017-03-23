package com.androidapiexamjykim.androidapiexam;

import com.androidapiexamjykim.androidapiexam.Model.WeatherModel;
import com.androidapiexamjykim.androidapiexam.Model2.WeatherModel2;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by user on 2017-03-13.
 */

public interface Api {

    String BASE_URL = "http://api.openweathermap.org/data/2.5/";
    String API_KEY = "32fb8e1d82d5ae7d4d58e28f4bd32f38";

    @GET("weather")
    Call<WeatherModel> getJson(@Query("APPID") String appId,
                               @Query("lat") double lat,
                               @Query("lon") double lon);

    @GET("forecast")
    Call<WeatherModel2> getJson2(@Query("APPID") String appId,
                                @Query("lat") double lat,
                                @Query("lon") double lon);


}
