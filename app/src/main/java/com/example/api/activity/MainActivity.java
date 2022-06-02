package com.example.api.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.api.ApiClient;
import com.example.api.BaseActivity;
import com.example.api.R;
import com.example.api.databinding.ActivityMainBinding;
import com.example.api.login.LoginRequest;
import com.example.api.login.LoginResponse;
import com.example.api.profile_info.ProfileResponse;
import com.example.api.utill.ProcessBar;
import com.example.api.utill.SharedPref;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding binding;
    public LoginResponse loginResponse;
    private SharedPref sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        SharedPref.getInstance().setContext(this);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    String username = SharedPref.getInstance().getString("username", "");
                    if (!username.equals("")) {
                        startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                        finish();
                    }
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                }
            }
        });

        binding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    onStartProgress("Logging in....");
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
                                onStopProgress();
                                int status = response.body().getStatus();
                                if (status == 200) {
                                    //Store the user info
                                    Log.e("Error", "" + response.body().getUserData().getUserId());
//                                    SharedPref.getInstance().putInt("userId", response.body().getUserData().getUserId());
                                    SharedPref.getInstance().putString("userId", String.valueOf(response.body().getUserData().getUserId()));
                                    SharedPref.getInstance().putString("username", response.body().getUserData().getUsername());
                                    SharedPref.getInstance().putString("email", response.body().getUserData().getEmail());
                                    SharedPref.getInstance().putString("dateOfBirth", response.body().getUserData().getDateOfBirth());
                                    SharedPref.getInstance().putString("mobileNumber", response.body().getUserData().getMobileNumber());
//
                                    onAlertMessage("Message", response.body().getMessage(), new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                            Intent dashboardIntent = new Intent(getApplicationContext(), DashboardActivity.class);
                                            startActivity(dashboardIntent);
                                            finish();
                                            dialog.dismiss();
                                        }
                                    });
                                } else if (status == 400) {
                                    onAlertMessage("Message", response.body().getMessage());
                                } else {
                                    onAlertMessage("Alert!", response.body().getMessage());
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            onStopProgress();
                            Log.e("Value", "" + t.getMessage());
                            onAlertMessage("Alert!", t.getMessage());
                        }
                    });

                } catch (Exception e) {
                    onStopProgress();
                    Toast.makeText(MainActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("Error", e.getMessage());
                }
            }
        });

    }

//    protected void showCustomDialog(String message, boolean isSuccess, LoginResponse.UserData userData) {
//        final Dialog dialog = new Dialog(this);
//        dialog.setContentView(R.layout.custom_dialog);
//        dialog.setCancelable(true);
//        dialog.setTitle("");
//
//        TextView text = dialog.findViewById(R.id.textViewMessage);
//        text.setText(message);
//
//        ImageView image = dialog.findViewById(R.id.imageViewDialog);
//        if (isSuccess)
//            image.setImageResource(R.drawable.icon_success);
//        else
//            image.setImageResource(R.drawable.icon_warning);
//        dialog.show();
//
//        Button declineButton = dialog.findViewById(R.id.buttonOk);
//        declineButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    if (isSuccess) {
//                        Intent dashboardIntent = new Intent(getApplicationContext(), DashboardActivity.class);
//                        dashboardIntent.putExtra("userId", userData.getUserId());
//                        dashboardIntent.putExtra("email", userData.getEmail());
//                        dashboardIntent.putExtra("username", userData.getUsername());
//                        startActivity(dashboardIntent);
////                        startActivity(new Intent(getApplicationContext(),DashboardActivity.class));
////                        finish();
//
//                    }
//                    dialog.dismiss();
//                } catch (Exception exception) {
//                    Log.e("Error ==> ", "" + exception);
//                }
//
//            }
//        });
//    }

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