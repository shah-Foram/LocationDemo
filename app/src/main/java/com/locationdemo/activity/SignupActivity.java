package com.locationdemo.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.locationdemo.R;
import com.locationdemo.model.UserModel;
import com.locationdemo.util.Preference;
import com.locationdemo.util.Utils;


/***
 * Firebase registration class
 */
public class SignupActivity extends BaseActivity implements View.OnClickListener {

    private TextInputEditText etdName;
    private TextInputEditText etdEmail;
    private TextInputEditText etdPhoneNumber;
    private TextInputEditText etdPasword;
    private TextInputEditText etdConfirmPasword;
     private ProgressDialog progressDialog;

    private Button btnSignUp;
    private Button btnLogin;

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
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getApplicationContext().getResources().getString(R.string.please_wait));
        progressDialog.setCanceledOnTouchOutside(false);
        btnLogin = findViewById(R.id.activity_signUp_btnLogin);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Utils.hideSoftKeyBoard(SignupActivity.this,v);
            if(v == btnSignUp)
            {
                if (!checkValidation()) {
                    return;
                }
                progressDialog.show();
                signUp(etdEmail.getText().toString(),etdPasword.getText().toString());
            }
            if(v== btnLogin)
            {
                startActivity(new Intent(this,LoginActivity.class));
                finish();
            }
    }


    /***
     * Call firebase default registration method for new users and also push user's other information in user table
     * @param email
     * @param password
     */
    private void signUp(String email, final String password) {
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    progressDialog.dismiss();
                    String userId = task.getResult().getUser().getUid();
                    Preference.getInstance().setUserId(userId);
                    // creating user object
                    UserModel user = new UserModel(etdName.getText().toString(),etdPhoneNumber.getText().toString());
                    // pushing user to 'users' node using the userId
                    databaseReference.child(userId).setValue(user);
                    Toast.makeText(getApplicationContext(),  getApplicationContext().getResources().getString(R.string.signup_sucessful),Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                }
                else {
                    progressDialog.dismiss();
                    Snackbar.make(btnSignUp,
                            getApplicationContext().getResources().getString(R.string.somthing_went_wrong),
                            Snackbar.LENGTH_INDEFINITE).show();
                }
            }
        });
    }


    /***
     * Check basic validations
     * @return
     */
    private boolean checkValidation() {

        boolean result = true;
        if (TextUtils.isEmpty(etdEmail.getText().toString())) {
            etdEmail.setError(getString(R.string.str_required));
            result = false;
        } else {
            if(Utils.isValidEmailId(etdEmail.getText().toString()))
            {
                etdEmail.setError(null);
            }
            else {
                etdEmail.setError(getString(R.string.enter_valid_email));
                result =false;
            }
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
            if (etdPasword.length() < 6) {
                etdPasword.setError(this.getApplicationContext().getString(R.string.password_requirement_msg));
                return false;
            } else {
                etdPasword.setError(null);
            }
        }

        if (TextUtils.isEmpty(etdConfirmPasword.getText().toString())) {
            etdConfirmPasword.setError(getString(R.string.str_required));
            result = false;
        } else {
            etdConfirmPasword.setError(null);
        }
        if (!TextUtils.isEmpty(etdPasword.getText().toString()) && (!TextUtils.isEmpty(etdConfirmPasword.getText().toString())) &&(etdPasword.getText().toString().equals(etdConfirmPasword.getText().toString())) )
        {
            etdConfirmPasword.setError(null);
        }
        else {
            etdConfirmPasword.setError(this.getApplicationContext().getString(R.string.confirm_password_error));
            result=false;
        }

        return result;
    }


}
