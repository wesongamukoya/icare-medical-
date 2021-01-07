package com.mukoya.icare;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class Loginpage extends AppCompatActivity {
    EditText editEmail, editPassword;
    Button btnSignup2;
    Button btnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);
        getSupportActionBar().setTitle("Login page");

        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        btnLogin = findViewById(R.id.btn_login);
        btnSignup2 = findViewById(R.id.btn_signup2);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password;
                email = editEmail.getText().toString();
                password = editPassword.getText().toString();

                if (email.equals("")){
                    Toast.makeText(Loginpage.this,"email required", Toast.LENGTH_SHORT);
                }
                else if (password.equals("")){
                    Toast.makeText(Loginpage.this,"password required", Toast.LENGTH_SHORT);

                }
                else {
                    Intent intent = new Intent(Loginpage.this,MainActivity.class);
                    startActivity(intent);

                }


            }
        });

    }

    public <view> void btn_signup2 (view view) {
        startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
    }
}
