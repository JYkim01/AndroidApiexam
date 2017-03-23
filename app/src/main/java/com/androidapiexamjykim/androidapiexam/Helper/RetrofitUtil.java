package com.androidapiexamjykim.androidapiexam.Helper;

import com.androidapiexamjykim.androidapiexam.Api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by user on 2017-03-13.
 */

public class RetrofitUtil {

    private Retrofit mRetrofit;

    private Api mApi;

    public RetrofitUtil () {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApi = mRetrofit.create(Api.class);
    }

    public Api getUserApi() {
        return mApi;
    }
}
