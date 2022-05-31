package com.example.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    static Retrofit getRetrofit(){

        HttpLoggingInterceptor httpLoggingInterceptor=new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient=new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();

        Retrofit retrofit=new Retrofit
                .Builder()
                .baseUrl("http://192.168.5.23:4000")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

//        192.168.1.112

        return retrofit;
    }

    public static UserService getUserService(){
        UserService userService=getRetrofit().create(UserService.class);
        return  userService;
    }

}
