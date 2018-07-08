package com.agriculture.ek.agriculture;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ProfileActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    String token;
    DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) {

            startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
            finish();
        }


        token = firebaseAuth.getCurrentUser().getUid();

        myRef = FirebaseDatabase.getInstance().getReference("Services");
        myRef.child("Advertisements").child("advertisement_1").child("advertisement_user_id").setValue(token);
        myRef.child("Advertisements").child("advertisement_1").child("title").setValue("Ziraat Mühendisi");
        myRef.child("Advertisements").child("advertisement_1").child("desc").setValue("Makinalar hakkında bilgili çalışma arkadaşı arıyoruz");
        myRef.child("Advertisements").child("advertisement_1").child("department").setValue("Biosistem Mühendisi");
        myRef.child("Advertisements").child("advertisement_1").child("experiance").setValue("2 yıllık deneyimli");
        myRef.child("Advertisements").child("advertisement_1").child("soldiar_status").setValue("Askerlikle ilişiği bulunayan veya 2 yıl Tecilli");


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ProfileActivity.this, HomeActivity.class));
        finish();
    }
}
