package com.example.selokisaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{

    private FirebaseAuth mAuth;
    private TextView register, forgotPassword;
    private EditText userEmail, userPassword;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        userEmail = findViewById(R.id.userEmail);
        userPassword = findViewById(R.id.userPassword);

        login = findViewById(R.id.resetButton);
        login.setOnClickListener(this);

        register = (TextView) findViewById(R.id.register);
        register.setOnClickListener(this);

        forgotPassword = (TextView) findViewById(R.id.forgotPassword);
        forgotPassword.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.resetButton:
               UserLogin();//Execute the welcome class here.
                break;
            case R.id.register:
                startActivity(new Intent(this,Register.class)); //Execute the register class here.
                break;
            case R.id.forgotPassword:
                startActivity(new Intent(this,ForgotPassword.class));
                break;
        }

    }

    private void UserLogin() {
        final String editEmail = userEmail.getText().toString().trim();
        final String editPassword = userPassword.getText().toString().trim();

        if(editEmail.isEmpty()){
            userEmail.setError("Email is required");
            userEmail.requestFocus();
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(editEmail).matches()){
            userEmail.setError("Please provide valid email!");
            userEmail.requestFocus();
            return;
        }
        if(editPassword.isEmpty()){
            userPassword.setError("Password is required");
            userPassword.requestFocus();
        }
        if (editPassword.length() < 6){
            userPassword.setError("Length should be 6 characters");
            userPassword.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(editEmail,editPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

                    if (firebaseUser.isEmailVerified())
                    {
                        //Redirect to user Profile
                        startActivity(new Intent(MainActivity.this, Profile.class));
                    }
                    else{
                        //Verify email for security reasons
                        firebaseUser.sendEmailVerification();
                        Toast.makeText(MainActivity.this,"Please check your email.",Toast.LENGTH_LONG).show();
                    }

                }
                else{
                    Toast.makeText(MainActivity.this,"Failed to login",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
