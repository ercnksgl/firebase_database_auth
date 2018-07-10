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

import java.util.ArrayList;
import java.util.List;


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
        databaseReference = FirebaseDatabase.getInstance().getReference("Services").child("Advertisements");
        final List<String> deneme = new ArrayList<>();
        deneme.add("Yeni bir İş ilanı aldınız");
        deneme.add("Yeni bir İş ilanı aldınız");
        deneme.add("Yeni bir İş ilanı aldınız");

        databaseReference.child("ilanlar").setValue(deneme);
        deneme.clear();

        databaseReference.child("ilanlar").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Is better to use a List, because you don't know the size
                // of the iterator returned by dataSnapshot.getChildren() to
                // initialize the array
                final List<String> areas = new ArrayList<>();
                deneme.clear();
                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                    String areaName = areaSnapshot.getValue(String.class);
                    areas.add(areaName);
                    deneme.add(areaName);
                    deneme.add("yeni ilan");
                    deneme.add("ohh be");
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference.child("ilanlar").setValue(deneme);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String title = dataSnapshot.child("Advertisements").child("advertisement_1").child("title").getValue(String.class);
                String soldiar_status = dataSnapshot.child("Advertisements").child("advertisement_1").child("soldiar_status").getValue(String.class);
                String desc = dataSnapshot.child("Advertisements").child("advertisement_1").child("desc").getValue(String.class);

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
