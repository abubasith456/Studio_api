package com.example.api;
import com.example.api.login.LoginRequest;
import com.example.api.login.LoginResponse;
import com.example.api.profile_info.ProfileResponse;
import com.example.api.register.RegisterRequest;
import com.example.api.register.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserService {

    @POST("/register")
    Call<RegisterResponse> register(@Body RegisterRequest registerRequest);


    @POST("/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @GET("/profile")
    Call<ProfileResponse> profile();


}
