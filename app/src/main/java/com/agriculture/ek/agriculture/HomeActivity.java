package com.agriculture.ek.agriculture;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {
DatabaseReference databaseReference;
FirebaseAuth firebaseAuth;
private String token;
TextView name_surname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        name_surname=findViewById(R.id.home_title_text);
        firebaseAuth=FirebaseAuth.getInstance();
        if(firebaseAuth!=null){
            token=firebaseAuth.getCurrentUser().getUid();
        }

        databaseReference=FirebaseDatabase.getInstance().getReference("Services");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("Users").child(token).child("name").getValue(String.class);
                String surname = dataSnapshot.child("Users").child(token).child("surname").getValue(String.class);


                name_surname.setText( "Hoşgeldin\n" +name + " " +surname);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}
