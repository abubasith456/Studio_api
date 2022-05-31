package com.example.api.utill;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {

    private static SharedPref instance;
    private Context context;

    public static SharedPref getInstance() {
        if (instance == null) {
            instance = new SharedPref();
        }
        return instance;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void putString(String tag, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putString(tag, value);
        myEdit.commit();
    }

    public String getString(String tag, String defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPref", MODE_PRIVATE);
        return sharedPreferences.getString(tag, defaultValue);
    }

    public void putInt(String tag, int value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putInt(tag, value);
    }


    public int getInt(String tag, int defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPref", MODE_PRIVATE);
        return sharedPreferences.getInt(tag, defaultValue);
    }

}
