package com.example.task1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class OTPVerification extends AppCompatActivity {

    EditText ed1;
    Button tv1;
    String otp_generated = "abcd";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverification);

        ed1 = findViewById(R.id.otp);
        tv1 = findViewById(R.id.verify_otp);
    /*This is important because this will be called every time you receive
     any sms*/
        MyReceiver.bindListener(new smslistener() {
            @Override
            public void messageReceived(String messageText) {
                Toast.makeText(OTPVerification.this, "message recieved here", Toast.LENGTH_SHORT).show();
                ed1.setText(messageText);
            }
        });
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    InputMethodManager imm =
                            (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                }
                if (ed1.getText().toString().equals(otp_generated)) {
                    Toast.makeText(OTPVerification.this, "OTP Verified Successfully !", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(OTPVerification.this, home.class));
                }
            }

        });


    }
}
