package com.mukoya.icare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class SignUpActivity extends AppCompatActivity {

    EditText etName, etEmail, etPhone, etResidence;
    TextView tvName, tvEmail, tvPhone, tvResidence;
    Button btnSignup;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etName =findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etPhone = findViewById(R.id.et_phone);
        etResidence = findViewById(R.id.et_residence);
        tvName = findViewById(R.id.tv_name);
        tvEmail = findViewById(R.id.tv_email);
        tvPhone= findViewById(R.id.tv_phone);
        tvResidence = findViewById(R.id.tv_residence);
        btnSignup = findViewById(R.id.btn_signup);
        spinner = findViewById(R.id.spinner);

        btnSignup. setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //start progressBar first (set Visibility VISIBLE)

                Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        String name, email, residence;
                        Editable phonenumber;
                        name = String.valueOf(etName.getText());
                        email = String.valueOf(etEmail.getText());
                        residence = String.valueOf(etResidence.getText());
                        phonenumber = etPhone.getText();
                        //starting write and read data url
                        //creating array for parameters
                        String [] field = new String[5];
                        field[0] = "name";
                        field[1] = "email";
                        field[2] = "phone number";
                        field[3] = "Residence";
                        field[4] = "patient/ doctor";
                        //creating array for data
                        String[] data = new String[2];
                        data[0] = "data-1";
                        data[1] = "data-2";
                        PutData putData = new PutData ("https://projects.mukoya.com/AdvancedHttpUrlConnection/putDataTest.php",
                                "POST", field, data);

                        if (putData.startPut()){
                            if (putData.onComplete()){
                                String result = putData.getResult();

                                //end progress bar (set visibility to Gone)
                                Log.i("putData", result);
                            }
                        }

                        //end write and read data with URL


                    }
                });

            }
        });


    }
}
