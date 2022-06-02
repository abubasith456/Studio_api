package com.example.api.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.api.ApiClient;
import com.example.api.BaseActivity;
import com.example.api.BaseFragment;
import com.example.api.R;
import com.example.api.databinding.FragmentProfileBinding;
import com.example.api.login.LoginResponse;
import com.example.api.update_profile.UpdateProfileRequest;
import com.example.api.update_profile.UpdateProfileResponse;
import com.example.api.utill.SharedPref;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends BaseFragment {

    FragmentProfileBinding binding;
    String username, email, dateOfBirth, mobileNumber;
    int user_id;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Get details
        user_id = Integer.parseInt(SharedPref.getInstance().getString("userId", ""));
        username = SharedPref.getInstance().getString("username", "");
        email = SharedPref.getInstance().getString("email", "");
        dateOfBirth = SharedPref.getInstance().getString("dateOfBirth", "");
        mobileNumber = SharedPref.getInstance().getString("mobileNumber", "");

        //Set details
        binding.editTextEmail.setText(email);
        binding.edittextUserName.setText(username);
        binding.editTextDateOfBirth.setText(dateOfBirth);
        binding.editTextMobileNumber.setText(mobileNumber);

        Log.e("UserID", String.valueOf(user_id));

        //Update profile
        binding.buttonUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    onStartProgress("Updating...");
                    UpdateProfileRequest updateProfileRequest = new UpdateProfileRequest();
                    updateProfileRequest.setUserId(user_id);
                    updateProfileRequest.setUsername(username.equals("") ? binding.edittextUserName.getText().toString() : username);
                    updateProfileRequest.setDateOfBirth(dateOfBirth.equals("") ? binding.editTextDateOfBirth.getText().toString() : dateOfBirth);
                    updateProfileRequest.setMobileNumber(mobileNumber.equals("") ? binding.editTextMobileNumber.getText().toString() : mobileNumber);

                    Call<UpdateProfileResponse> loginResponseCall = ApiClient.getUserService().updateProfile(updateProfileRequest);
                    loginResponseCall.enqueue(new Callback<UpdateProfileResponse>() {
                        @Override
                        public void onResponse(Call<UpdateProfileResponse> call, Response<UpdateProfileResponse> response) {
                            if (response.isSuccessful()) {
                                if (response.body().getStatus() == 200) {
                                    onStopProgress();
                                    onAlertMessage("Message", response.body().getMessage());
                                } else {
                                    onStopProgress();
                                    onAlertMessage("Alert!", response.body().getMessage());
                                }
                            } else {
                                onAlertMessage("Alert!", response.body().getMessage());
                                onStopProgress();
                            }
                        }

                        @Override
                        public void onFailure(Call<UpdateProfileResponse> call, Throwable t) {
                            onAlertMessage("Alert!", t.getMessage());
                            onStopProgress();
                        }
                    });

                } catch (Exception e) {
                    onStopProgress();
                    Log.e("Error", "" + e.getMessage());
                }
            }
        });
    }
}