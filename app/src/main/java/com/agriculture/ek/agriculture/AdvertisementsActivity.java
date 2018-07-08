package com.agriculture.ek.agriculture;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class AdvertisementsActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertisement);
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) {

            startActivity(new Intent(AdvertisementsActivity.this, LoginActivity.class));
            finish();
        }
        databaseReference = FirebaseDatabase.getInstance().getReference("Services");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String title= dataSnapshot.child("Advertisements").child("advertisement_1").child("title").getValue(String.class);
                String soldiar_status= dataSnapshot.child("Advertisements").child("advertisement_1").child("soldiar_status").getValue(String.class);
                String desc= dataSnapshot.child("Advertisements").child("advertisement_1").child("desc").getValue(String.class);

                Toast.makeText(AdvertisementsActivity.this, ""+ title + " \n"+ desc + "\n"+ soldiar_status, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(AdvertisementsActivity.this, HomeActivity.class));
        finish();
    }
}
