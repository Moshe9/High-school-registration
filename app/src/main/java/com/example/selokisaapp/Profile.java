package com.example.selokisaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {

    private Button logout;

    private FirebaseUser userProfile;
    private DatabaseReference databaseReference;
    private String userID;

    TextView welcomeUser,emailProfile, nameProfile, phoneProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        logout = findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                //Redirect to Main Activity
                startActivity(new Intent(Profile.this, MainActivity.class));
            }
        });

        userProfile = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        userID = userProfile.getUid();

         welcomeUser =  findViewById(R.id.welcomeUser);
         emailProfile = findViewById(R.id.emailProfile);
         nameProfile = findViewById(R.id.nameProfile);
         phoneProfile = findViewById(R.id.phoneProfile);

        //Get data from user database
        databaseReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                User userProfile = dataSnapshot.getValue(User.class);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Profile.this, "Login Failed!,try again",Toast.LENGTH_LONG).show();
            }
        });


    }
}
