package com.locationdemo.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.locationdemo.AndroidApp;
import com.locationdemo.R;

/**
 * <p>
 * Purpose of this class is to save data in preference and retrieve values from preference throughout the lifecycle of application
 * This class is hold methods for storing and retrieving values from preference.
 * </p>
 */
public class Preference {
    public static final String PREFERENCE_LANG_ID = "LANG_ID";
    private static Preference preference;
    public final String PREFERENCE_USER_IS_LOGIN = "USER_IS_LOGIN";
    /**
     * Preference key for userId
     */
    private final String PREFERENCE_USER_ID = "USER_ID";
    private SharedPreferences sharedPreferences;

    private Preference() {
        sharedPreferences = AndroidApp.getInstance().getSharedPreferences(AndroidApp.getInstance().getString(R.string.app_name), Context.MODE_PRIVATE);
    }

    /**
     * @return the {@link SharedPreferences} object that will be used to save values in the application preference
     */
    public static Preference getInstance() {
        if (preference == null) {
            preference = new Preference();
        }
        return preference;
    }

    /**
     * Returns the userId from the Shared Preference file
     *
     * @return userId
     */
    public String getUserId() {
        return sharedPreferences.getString(PREFERENCE_USER_ID, "");
    }

    /**
     * Stores the userId into Shared Preference file
     */
    public void setUserId(final String userId) {
        sharedPreferences.edit().putString(PREFERENCE_USER_ID, userId).apply();
    }

    /**
     * Stores the  value in the preference
     *
     * @param key    parameter for the key for the values in preference
     * @param value  parameter for the value to be stored in preference
     */
    public void setData(String key, String value) {
        sharedPreferences.edit().putString(key, value).apply();
    }

    public void getData(String key, String defaultValue) {
        sharedPreferences.getString(key, defaultValue);
    }

    /**
     * clearAllPreferenceData : it will clear all data from preference
     */
    public void clearAllPreferenceData() {
        sharedPreferences.edit().clear().apply();
    }


}
