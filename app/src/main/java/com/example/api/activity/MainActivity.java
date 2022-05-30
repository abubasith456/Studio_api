package com.example.api.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.api.ApiClient;
import com.example.api.R;
import com.example.api.databinding.ActivityMainBinding;
import com.example.api.login.LoginRequest;
import com.example.api.login.LoginResponse;
import com.example.api.profile_info.ProfileResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    public LoginResponse loginResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    String email = binding.editTextMobileNumber.getText().toString();
                    String password = binding.editTextPassword.getText().toString();

                    LoginRequest loginRequest = new LoginRequest();
                    loginRequest.setEmail(email);
                    loginRequest.setPassword(password);

                    Call<LoginResponse> loginResponseCall = ApiClient.getUserService().login(loginRequest);
                    loginResponseCall.enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                            if (response.isSuccessful()) {
                                int status = response.body().getStatus();
                                if (status == 200) {
                                    loginResponse = response.body();
                                    showCustomDialog(response.body().getMessage(), true);
                                } else if (status == 400) {
                                    showCustomDialog(response.body().getMessage(), false);
                                } else {
                                    showCustomDialog("Something went wrong", false);
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            Log.e("Value", "" + t.getMessage());
                            showCustomDialog("Something went wrong. Please try again later!", false);
                        }
                    });

                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                }
            }
        });

    }

    protected void showCustomDialog(String message, boolean isSuccess) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setTitle("");

        TextView text = dialog.findViewById(R.id.textViewMessage);
        text.setText(message);

        ImageView image = dialog.findViewById(R.id.imageViewDialog);
        if (isSuccess)
            image.setImageResource(R.drawable.icon_success);
        else
            image.setImageResource(R.drawable.icon_warning);
        dialog.show();

        Button declineButton = dialog.findViewById(R.id.buttonOk);
        declineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (isSuccess) {
                        Intent dashboardIntent = new Intent(getApplicationContext(), DashboardActivity.class);
                        dashboardIntent.putExtra("userId",loginResponse.getUser_id());
                        dashboardIntent.putExtra("email", loginResponse.getEmail());
                        dashboardIntent.putExtra("username",loginResponse.getUsername());
                        startActivity(dashboardIntent);
//                        startActivity(new Intent(getApplicationContext(),DashboardActivity.class));
//                        finish();

                    }
                    dialog.dismiss();
                } catch (Exception exception) {
                    Log.e("Error ==> ", "" + exception);
                }

            }
        });
    }

    private void getInfo() {
        try {
            Call<ProfileResponse> profileResponseCall = ApiClient.getUserService().profile();
            profileResponseCall.enqueue(new Callback<ProfileResponse>() {
                @Override
                public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                    Log.e("onResponse", "" + response.body().getMessage());
                }

                @Override
                public void onFailure(Call<ProfileResponse> call, Throwable t) {
                    Log.e("onResponse", "" + t.getMessage());
                }
            });
        } catch (Exception e) {
            Log.e("onResponse", "" + e.getMessage());
        }
    }
}