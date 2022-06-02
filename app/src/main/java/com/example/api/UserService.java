package com.example.api;

import com.example.api.login.LoginRequest;
import com.example.api.login.LoginResponse;
import com.example.api.logout.LogoutRequest;
import com.example.api.logout.LogoutResponse;
import com.example.api.profile_info.ProfileResponse;
import com.example.api.register.RegisterRequest;
import com.example.api.register.RegisterResponse;
import com.example.api.update_profile.UpdateProfileRequest;
import com.example.api.update_profile.UpdateProfileResponse;

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

    @POST("/update")
    Call<UpdateProfileResponse> updateProfile(@Body UpdateProfileRequest updateProfileRequest);

    @POST("/logout")
    Call<LogoutResponse> logout(@Body LogoutRequest logoutRequest);


}
