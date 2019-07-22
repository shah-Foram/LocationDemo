package com.locationdemo.activity;

import androidx.annotation.NonNull;

import android.app.ProgressDialog;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.locationdemo.R;
import com.locationdemo.util.Utils;

import java.util.logging.Handler;

/***
 * Firebase login class
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private TextInputEditText  etdEmail;
    private TextInputEditText etdPassword;
    private Button btnLogin;
    private Button btnSignUp;
    private ProgressDialog progressDialog;
    @Override
    protected int defineLayoutResource() {
        return R.layout.activity_login;
    }

    @Override
    protected void initializeComponents() {
        etdEmail = findViewById(R.id.activity_login_etdEmail);
        etdPassword = findViewById(R.id.activity_login_etdPassword);
        btnLogin = findViewById(R.id.activity_login_btnLogin);
        btnSignUp = findViewById(R.id.activity_login_btnSignUp);
        btnLogin.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getApplicationContext().getResources().getString(R.string.please_wait));
        progressDialog.setCanceledOnTouchOutside(false);
        firebaseAuth = FirebaseAuth.getInstance();
    }


    @Override
    public void onClick(View v) {
        Utils.hideSoftKeyBoard(LoginActivity.this,v);
            if(v == btnLogin)
            {
                if(!checkValidation())
                {
                    return;
                }
                progressDialog.show();
                signIn(etdEmail.getText().toString(), etdPassword.getText().toString());
            }
            if(v== btnSignUp)
            {
                startActivity(new Intent(this,SignupActivity.class));
                finish();
            }
    }


    /***
     * Signin in firebase using email and password
     * @param email
     * @param password
     */
    private void signIn(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();

                            Toast.makeText(getApplicationContext(),  getApplicationContext().getResources().getString(R.string.login_sucessful),Toast.LENGTH_LONG).show();

                            startActivity( new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        }
                        else {
                          progressDialog.dismiss();
                                Snackbar.make(btnLogin,
                                        getApplicationContext().getResources().getString(R.string.invalid_login_msg),
                                        Snackbar.LENGTH_INDEFINITE).show();
                        }
                    }
                });

    }

    /***
     * check basic validation before signin
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
               result = false;
            }
        }
        if (TextUtils.isEmpty(etdPassword.getText().toString())) {
            etdPassword.setError(getString(R.string.str_required));
            result = false;
        } else {
            etdPassword.setError(null);
        }
        return result;
    }
}
