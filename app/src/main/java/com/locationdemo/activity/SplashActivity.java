package com.locationdemo.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.locationdemo.R;


/**
 * Simple startup activity showing static image.
 */
public class SplashActivity extends AppCompatActivity {

    /*
     * Handler is used to set some delay on this screen
     */
    private Handler handler;

    protected FirebaseAuth firebaseAuth;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(firebaseAuth.getCurrentUser()!=null){
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
                finish();
            }
            else {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
               finish();
            }
        }
    };



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        firebaseAuth = FirebaseAuth.getInstance();
        if (isDuplicateInstance()) {//return if this is duplicate instance of same category and instance
            return;
        }
        executeHandler();
    }

    private void executeHandler() {
        handler = new Handler();
        long INTERVAL = 2000;
        handler.postDelayed(runnable, INTERVAL);
    }


    /**
     * This method will prevent multiple instances of an activity when it is launched with different intents
     */
    private boolean isDuplicateInstance() {
        if (!isTaskRoot()) {
            final Intent intent = getIntent();
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN.equals(intent.getAction())) {
                finish();
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacks(runnable);
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
