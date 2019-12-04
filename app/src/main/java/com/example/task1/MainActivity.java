package com.example.task1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUESTS_RECEIVE_SMS=0;

    EditText email, password;
    Button signin, signup;
    FirebaseAuth mfirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onStart() {
        super.onStart();
        mfirebaseAuth.addAuthStateListener(mAuthStateListener);
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS)!= PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.RECEIVE_SMS))
            {

            }
            else
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS},MY_PERMISSIONS_REQUESTS_RECEIVE_SMS);
            }
        }


        mfirebaseAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.editText);
        password = findViewById(R.id.editText2);
        signin = findViewById(R.id.button);
        signup = findViewById(R.id.button2);

        signup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent is = new Intent(getApplicationContext(), login.class);
                startActivity(is);
            }
        });

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFireBaseUser =mfirebaseAuth.getCurrentUser();
                if(mFireBaseUser !=null)
                {
                    Toast.makeText(MainActivity.this, "You are logged in", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MainActivity.this, home.class);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(MainActivity.this, "please use valid email and password", Toast.LENGTH_SHORT).show();
                }
            }
        };

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailId=email.getText().toString();
                String pwd=password.getText().toString();
                if(emailId.isEmpty())
                {
                    email.setError("please enter valid email id");
                    email.requestFocus();
                }
                else if(pwd.isEmpty())
                {
                    password.setError("please enter correct password");
                    password.requestFocus();
                }
                else if (!(emailId.isEmpty() && pwd.isEmpty()))
                {
                    mfirebaseAuth.signInWithEmailAndPassword(emailId,pwd).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful())
                            {
                                Toast.makeText(MainActivity.this, "Login Error, please try again", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Intent intenthome = new Intent(MainActivity.this, OTPVerification.class);
                                startActivity(intenthome);
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Error occured", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantresults)
    {
        switch (requestCode)
        {
            case MY_PERMISSIONS_REQUESTS_RECEIVE_SMS:
            {
                if (grantresults.length>0 && grantresults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    //permission granted
                }
                else
                {
                    //permission still no granted
                }
            }
        }
    }

}
