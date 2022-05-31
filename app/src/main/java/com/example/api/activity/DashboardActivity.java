package com.example.api.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.api.ApiClient;
import com.example.api.R;
import com.example.api.databinding.ActivityDashboardBinding;
import com.example.api.login.LoginResponse;
import com.example.api.logout.LogoutRequest;
import com.example.api.logout.LogoutResponse;
import com.example.api.profile_info.ProfileResponse;
import com.example.api.utill.SharedPref;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {

    int userId;
    String username, email;
    private ActivityDashboardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_dashboard);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard);
        SharedPref.getInstance().setContext(this);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });

        //Get user info
        int userId = SharedPref.getInstance().getInt("userId", 0);
        String username = SharedPref.getInstance().getString("username", "");
        String email = SharedPref.getInstance().getString("email", "");

        Glide.with(this).load(R.drawable.loading).into(binding.imageviewLoading);
        binding.textViewEmail.setText(email);
        binding.textViewUsername.setText(username);

        binding.buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    LogoutRequest logoutRequest = new LogoutRequest();
                    logoutRequest.setUserId(userId);
                    Call<LogoutResponse> logoutResponseCall = ApiClient.getUserService().logout(logoutRequest);
                    logoutResponseCall.enqueue(new Callback<LogoutResponse>() {
                        @Override
                        public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {
                            if (response.isSuccessful()) {
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                                SharedPref.getInstance().putInt("userId", 0);
                                SharedPref.getInstance().putString("username", "");
                                SharedPref.getInstance().putString("email", "");
                            }
                        }

                        @Override
                        public void onFailure(Call<LogoutResponse> call, Throwable t) {
                            Toast.makeText(DashboardActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                }
            }
        });
    }

}