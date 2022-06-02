package com.example.api;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

public class BaseFragment extends Fragment {

    private Activity activity;
    MaterialDialog progressDialog;
    MaterialDialog confirmDialog;
    MaterialDialog alertDialog;
    MaterialDialog errorDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = getActivity();
    }
    public void onStartProgress(final String message) {
        onStartProgress(message, getResources().getColor(R.color.black));
    }

    public void onStartProgress(final String message, int resId) {

        progressDialog = new MaterialDialog.Builder(activity)
                .cancelable(false)
                .canceledOnTouchOutside(false)
                .content(message)
                .widgetColor(resId)
                .progress(true, 0)
                .show();
    }


    public void onAlertMessage(final String title, final String message) {
        onAlertMessage(title, message, new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                dialog.dismiss();
            }
        });
    }

    public void onAlertMessage(final String title, final String message, final MaterialDialog.SingleButtonCallback callback) {
       getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                closeOpenDialogs();
                alertDialog = new MaterialDialog.Builder(activity)
                        .title(title)
                        .cancelable(false)
                        .canceledOnTouchOutside(false)
                        .content(message)
                        .contentColor(getResources().getColor(R.color.black))
                        .canceledOnTouchOutside(false)
                        .positiveColor(getResources().getColor(R.color.black))
                        .positiveText("OK")
                        .onPositive(callback)
                        .build();
                alertDialog.getTitleView().setTextSize(16);
                alertDialog.getContentView().setTextSize(14);
                alertDialog.show();
            }
        });
    }

    public void onConfirm(final String title, final String message, final String positiveText, final String negativeText, final MaterialDialog.SingleButtonCallback positiveCallback, final MaterialDialog.SingleButtonCallback negativeCallback) {
        try {
//            closeOpenDialogs();
            if (confirmDialog == null) {
                confirmDialog = new MaterialDialog.Builder(activity)
                        .cancelable(false)
                        .canceledOnTouchOutside(false)
                        .title(title)
                        .content(message)
                        .canceledOnTouchOutside(false)
                        .positiveColor(getResources().getColor(R.color.black))
                        .negativeColor(getResources().getColor(R.color.black))
                        .positiveText(positiveText)
                        .negativeText(negativeText)
                        .onPositive(positiveCallback)
                        .onNegative(negativeCallback)
                        .build();
                confirmDialog.show();
            } else {
                confirmDialog.show();
            }
        } catch (Exception err) {
            Log.e("BaseActivity:OnError", err.getMessage());
        }
    }

    public void closeOpenDialogs() {
        if (confirmDialog != null) {
            confirmDialog.dismiss();
            confirmDialog = null;
        }
        if (alertDialog != null) {
            alertDialog.dismiss();
            alertDialog = null;
        }
        if (errorDialog != null) {
            errorDialog.dismiss();
            errorDialog = null;
        }
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
        onStopProgress();
    }

    public void onStopProgress() {

        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }

    }

    public void hideSoftKeyboard() {
        if (getActivity().getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }
}
