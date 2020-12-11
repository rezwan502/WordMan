package com.example.app_wordman.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitApiClient {

    private static final String BASE_URL = "https://api.dictionaryapi.dev/api/v2/";
    private static Retrofit retrofit = null;

    private static Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    private RetrofitApiClient() {} // So that nobody can create an object with constructor

    public static Retrofit getClient() {
        if (retrofit == null) {
            synchronized (RetrofitApiClient.class) { //thread safe Singleton implementation
                if (retrofit == null) {
                    retrofit = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .build();
                }
            }
        }

        return retrofit;
    }

}