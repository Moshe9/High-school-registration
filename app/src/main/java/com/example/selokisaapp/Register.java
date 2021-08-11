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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity  implements View.OnClickListener{

    private FirebaseAuth mAuth;
    private Button registerUser;
    private EditText email,name,password,mobilePhone;
    private TextView lin;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.email);
        name =  findViewById(R.id.name);
        password =  findViewById(R.id.password);
        mobilePhone =  findViewById(R.id.mobilephone);

       registerUser = findViewById(R.id.registerUser);
       registerUser.setOnClickListener(this); //make the button clickable

       lin = findViewById(R.id.lin);
       lin.setOnClickListener(this); // make the textView clickable
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.registerUser:
                registerUser();
                  break;
            case R.id.lin:
                startActivity(new Intent(this,MainActivity.class)); //Execute the register class here.
                break;
        }
    }
    private void registerUser() {
        final String editEmail = email.getText().toString().trim();
        final String editName = name.getText().toString().trim();
        final String editPassword = password.getText().toString().trim();
        final String editMobilePhone = mobilePhone.getText().toString().trim();

        if(editEmail.isEmpty()){
            email.setError("Email is required");
            email.requestFocus();
        }
        //To check if the user has entered a valid email. Create "Patterns method" here.
        if(!Patterns.EMAIL_ADDRESS.matcher(editEmail).matches()){
            email.setError("Please provide valid email!");
            email.requestFocus();
            return;
        }

        if(editName.isEmpty()){
            name.setError("Name is required");
            name.requestFocus();
        }
        if(editPassword.isEmpty()){
            password.setError("Password is required");
            password.requestFocus();
        }
        if (editPassword.length() < 6){
            password.setError("Length should be 6 characters");
            password.requestFocus();
            return;
        }
        if(editMobilePhone.isEmpty()){
            mobilePhone.setError("Mobile Phone is required");
            mobilePhone.requestFocus();
        }
        //Create a new user and save to database here.
        registerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Store data into firebase.
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("Users");
                User userHelper = new User(editEmail,editName,editPassword,editMobilePhone);
                reference.child(editMobilePhone).setValue(userHelper);
            }
        });
        mAuth.createUserWithEmailAndPassword(editEmail, editPassword) //Create USER with email and password.
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            User user = new User(editEmail,editName,editPassword,editMobilePhone);//Work with String values from register user.
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    //SET VALUE TO SAVE TO DATABASE
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){

                                        Toast.makeText(Register.this, "Registration Successful",Toast.LENGTH_LONG).show();
                                        Register.this.finish();
                                        //Redirect user to login layout
                                    }
                                    else{

                                        Toast.makeText(Register.this, "Registration Failed!,try again",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                        else{
                            Toast.makeText(Register.this, "Registration Failed!,try again",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

}
