package com.locationdemo;

import android.app.Application;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * <p>Application class for define things which use till application running.</p>
 */
public class AndroidApp extends Application {

    private static AndroidApp androidApp;
    public static AndroidApp getInstance() {
        return androidApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
        androidApp = this;

// ...



    }


}
