package com.locationdemo.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.locationdemo.AndroidApp;
import com.locationdemo.R;
import com.locationdemo.model.Users;

public class SignupActivity extends BaseActivity implements View.OnClickListener {

    private TextInputEditText etdName;
    private TextInputEditText etdEmail;
    private TextInputEditText etdPhoneNumber;
    private TextInputEditText etdPasword;
    private TextInputEditText etdConfirmPasword;
    private ProgressBar pbProgress;
   private FirebaseAuth firebaseAuth;
   private  DatabaseReference databaseReference;
    private Button btnSignUp;

    @Override
    protected int defineLayoutResource() {
        return R.layout.activity_signup;
    }

    @Override
    protected void initializeComponents() {

        etdName = findViewById(R.id.etdName);
        etdEmail = findViewById(R.id.etdEmail);
        etdPhoneNumber = findViewById(R.id.etdPhoneNumber);
        etdPasword = findViewById(R.id.etdPassword);
        etdConfirmPasword = findViewById(R.id.etdConfirmPassword);
        btnSignUp = findViewById(R.id.btnSignup);
        btnSignUp.setOnClickListener(this);
        pbProgress = findViewById(R.id.pbProgress);
    }

    @Override
    public void onClick(View v) {
            if(v == btnSignUp)
            {
                setProgressBarVisible(true);
                if (!checkValidation()) {
                    setProgressBarVisible(false);
                    return;
                }
                signUp(etdEmail.getText().toString(),etdPasword.getText().toString());
            }
    }

    private void signUp(String email, String password) {
            databaseReference = FirebaseDatabase.getInstance().getReference("users");

        // Creating new user node, which returns the unique key value
        String userId = databaseReference.push().getKey();

            // creating user object
        Users user = new Users(etdName.getText().toString(),etdEmail.getText().toString(),etdPhoneNumber.getText().toString(),password);

        // pushing user to 'users' node using the userId
        databaseReference.child(userId).setValue(user);

    }


    private boolean checkValidation() {

        boolean result = true;
        if (TextUtils.isEmpty(etdEmail.getText().toString())) {
            etdEmail.setError(getString(R.string.str_required));
            result = false;
        } else {
            etdEmail.setError(null);
        }

        if (TextUtils.isEmpty(etdName.getText().toString())) {
            etdName.setError(getString(R.string.str_required));
            result = false;
        } else {
            etdName.setError(null);
        }


        if (TextUtils.isEmpty(etdPasword.getText().toString())) {
            etdPasword.setError(getString(R.string.str_required));
            result = false;
        } else {

            etdPasword.setError(null);
        }

        if (TextUtils.isEmpty(etdConfirmPasword.getText().toString())) {
            etdConfirmPasword.setError(getString(R.string.str_required));
            result = false;
        } else {
            etdConfirmPasword.setError(null);
        }


//        if (!TextUtils.isEmpty(etdPasword.getText().toString()) && (!TextUtils.isEmpty(etdConfirmPasword.getText().toString())))
//        {
//        }




        return result;


    }

    protected void setProgressBarVisible(final boolean isVisible) {
        if (pbProgress != null) {
            pbProgress.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        }
    }
}
