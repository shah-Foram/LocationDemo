package com.locationdemo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.locationdemo.R;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText  etdEmail;
    private TextInputEditText etdPasword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etdEmail = findViewById(R.id.etdEmail);
        etdPasword = findViewById(R.id.etdPassword);
        btnLogin = findViewById(R.id.btnLogin);


    }
}
