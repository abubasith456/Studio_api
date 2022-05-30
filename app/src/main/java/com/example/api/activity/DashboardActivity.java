package com.example.api.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.api.ApiClient;
import com.example.api.R;
import com.example.api.databinding.ActivityDashboardBinding;
import com.example.api.login.LoginResponse;
import com.example.api.profile_info.ProfileResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {

    String userId, username, email;
    private ActivityDashboardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_dashboard);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                userId = getIntent().getStringExtra("userId");
                username = getIntent().getStringExtra("username");
                email = getIntent().getStringExtra("email");
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                prefs.edit().putString("userId", userId);
                prefs.edit().putString("username", username);
                prefs.edit().putString("email", email);
            }
        });

        Glide.with(this).load(R.drawable.loading).into(binding.imageviewLoading);
        binding.textViewEmail.setText(email);
        binding.textViewUsername.setText(username);
    }

}