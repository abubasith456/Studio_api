package com.example.api.utill;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.ProgressBar;

import java.util.ConcurrentModificationException;

public class ProcessBar {

    private static ProcessBar instance;
    private ProgressDialog progressBar;

    public static ProcessBar getInstance() {
        if (instance == null) {
            instance = new ProcessBar();
        }
        return instance;
    }

    public void showProcess(Activity context, String text) {
        progressBar = new ProgressDialog(context);
        progressBar.setCancelable(true);
        progressBar.setMessage(text);
        progressBar.show();
    }

    public void dismissProcess() {
        if (progressBar != null) {
            progressBar.dismiss();
        }
    }
}
