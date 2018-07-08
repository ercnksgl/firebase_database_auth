package com.agriculture.ek.agriculture;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;


public class GiveBussinesAdActivity extends AppCompatActivity {

FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_bussines_ad);
        firebaseAuth=FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) {

            startActivity(new Intent(GiveBussinesAdActivity.this,LoginActivity.class));
            finish();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(GiveBussinesAdActivity.this,HomeActivity.class));
        finish();
    }
}
