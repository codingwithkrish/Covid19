package com.example.covid19.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Apiutilities {
    private static Retrofit retrofit = null;
    public static Apinterface getApiinterface(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(Apinterface.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit.create(Apinterface.class);

    }
}

