package com.dnd.moneyroutine.service;

import com.dnd.moneyroutine.custom.Common;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

// 서버 요청 관련 service class
public class RequestService {

    private static RequestService requestService = null;
    private Retrofit retrofit;

    private RetrofitService retrofitService;

    Gson gson = new GsonBuilder().setLenient().create();

    public RequestService() {
        retrofit = new Retrofit.Builder()
                .baseUrl(Common.SERVER)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitService = retrofit.create(RetrofitService.class);
    }

    public static RequestService getInstance() {
        if (requestService == null) {
            requestService = new RequestService();
        }

        return requestService;
    }

    public Call<String> test() {
        return  retrofitService.test();
    }
}
