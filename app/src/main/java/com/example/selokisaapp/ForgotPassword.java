package com.example.selokisaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {
    private EditText resetUserEmail;
    private Button resetButton;
    private FirebaseAuth mAuth;
    private TextView resetTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        resetUserEmail = findViewById(R.id.resetUserEmail);
        resetButton = findViewById(R.id.resetButton);
        resetTextView = findViewById(R.id.resetTextView);

        mAuth = FirebaseAuth.getInstance();

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });

    }
    private void resetPassword(){
        String email = resetUserEmail.getText().toString().trim();
        if (email.isEmpty()){
            resetUserEmail.setError("Email is required!");
            resetUserEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            resetUserEmail.setError("Please provide a valid email");
            resetUserEmail.requestFocus();
            return;
        }
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(ForgotPassword.this, "Check your email to reset password",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(ForgotPassword.this, "Please try again.",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}