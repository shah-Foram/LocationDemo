package com.locationdemo.util;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.locationdemo.R;


/**
 * Performs common utility operations.
 */
public class Utils {

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
