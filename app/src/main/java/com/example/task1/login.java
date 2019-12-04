package com.example.task1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {

    EditText email, password;
    Button register;
    FirebaseAuth mfirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mfirebaseAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.editText4);
        password = findViewById(R.id.editText5);
        register = findViewById(R.id.button4);

        register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                String emailId=email.getText().toString();
                String pwd=password.getText().toString();
                if(emailId.isEmpty())
                {
                    email.setError("please enter email id");
                    email.requestFocus();
                }
                else if(pwd.isEmpty())
                {
                    password.setError("please enter a valid password");
                    password.requestFocus();
                }
                else if (!(emailId.isEmpty() && pwd.isEmpty()))
                {
                    mfirebaseAuth.createUserWithEmailAndPassword(emailId,pwd).addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful())
                            {
                                Toast.makeText(login.this, "SignUp unsuccessful, please try again ", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(login.this, "Signed up successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(login.this, OTPVerification.class));
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(login.this, "Error occured", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
