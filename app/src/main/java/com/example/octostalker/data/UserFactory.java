package com.example.octostalker.data;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class UserFactory {
    private static final String URL_GITHUB = "https://api.github.com";
    public static UserService create() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(URL_GITHUB)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(UserService.class);
    }
}
