package com.example.api.forgot_password;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForgotResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("connection")
    @Expose
    private String connection;
    @SerializedName("message")
    @Expose
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getConnection() {
        return connection;
    }

    public void setConnection(String connection) {
        this.connection = connection;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
