package com.example.api.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginRequest {
    @SerializedName("email")
    private String email;

    @SerializedName("password")
    @Expose
    private String password;


    // Getter Methods

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    // Setter Methods

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
