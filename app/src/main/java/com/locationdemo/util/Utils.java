package com.locationdemo.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Settings;


import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;


import com.locationdemo.R;

import java.io.File;
import java.util.Locale;


/**
 * Performs common utility operations.
 */
public class Utils {

    /**
     * checks the GPS is enable or not
     *
     * @param activity    object required for get SystemService
     * @param showMessage if true will show enable GPS alert with got to settings option otherwise check silently
     * @return true if location enabled otherwise false
     */
    public static boolean checkLocationAccess(final Activity activity, boolean showMessage) {
        if (activity != null && !activity.isFinishing()) {
            final LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
            boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            boolean isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            if (isNetworkEnabled) {
                return true;
            } else if (isGpsEnabled) {
                return true;
            }

            if (showMessage) {
                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
                dialogBuilder.setTitle(activity.getString(R.string.app_name));
                dialogBuilder.setCancelable(false);
                dialogBuilder.setMessage(activity.getString(R.string.alert_check_gps));

                dialogBuilder.setPositiveButton(activity.getString(R.string.settings), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        activity.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                });

                dialogBuilder.setNegativeButton(activity.getString(android.R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface mDialog, int id) {
                        mDialog.dismiss();
                    }
                });
                dialogBuilder.show();

                return false;
            }
        }
        return false;
    }


    /**
     * Validates the Email Id
     *
     * @param emailId email id to be verified
     * @return true valid email id, false invalid emailid
     */
    public static boolean isValidEmailId(final String emailId) {
        return !TextUtils.isEmpty(emailId) && Patterns.EMAIL_ADDRESS.matcher(emailId).matches();
    }

    /**
     * Validates the Url
     *
     * @param url email id to be verified
     * @return true valid email id, false invalid emailid
     */
    public static boolean isValidUrl(final String url) {
        return !TextUtils.isEmpty(url) && Patterns.WEB_URL.matcher(url).matches();
    }


    /**
     * Hide the soft keyboard from screen for edit text only
     *
     * @param context context of current visible activity
     * @param view    clicked view
     */
    public static void hideSoftKeyBoard(final Context context, final View view) {
        try {
            final InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    /**
     * Show soft keyboard on auto focus of edittext
     *
     * @param context context of current visible activity
     * @param view    focused view
     */
    public static void showKeyboard(final Context context, final View view) {
        final InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }


}
