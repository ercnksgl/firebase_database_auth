package com.agriculture.ek.agriculture;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference myRef;
    FirebaseAuth firebaseAuth;
    private String token;
    private String email;
    private String pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        email = "ercnksgl14@gmail.com";
        pass = "13572468";


        firebaseAuth.createUserWithEmailAndPassword(email.trim(), pass.trim()).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "it's okay!You registered!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "problemu!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        firebaseAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            token = firebaseAuth.getCurrentUser().getUid();
                            Toast.makeText(MainActivity.this, "it's okayy. You Loged", Toast.LENGTH_SHORT).show();


                            Toast.makeText(MainActivity.this, "Veriler Kayıt edildi.", Toast.LENGTH_SHORT).show();
                            myRef = FirebaseDatabase.getInstance().getReference("Services");

                            myRef.child("Users").child(token).child("email").setValue(email);
                            myRef.child("Users").child(token).child("name").setValue("Ercannn");
                            myRef.child("Users").child(token).child("surname").setValue("Koseoglu");
                            myRef.child("Users").child(token).child("age").setValue(24);
                            myRef.child("Users").child(token).child("country").setValue("Turkey");
                            myRef.child("Users").child(token).child("city").setValue("Tokat");
                            myRef.child("Users").child(token).child("job").setValue("Biosistem engineer and Android Software developer");

                            myRef.child("Fields").child(token).child("field_name").setValue("field001");
                            myRef.child("Fields").child(token).child("decar").setValue(10);
                            myRef.child("Fields").child(token).child("irrigation_system").setValue("drip irrigation");

                            myRef.child("Fields").child(token).child("field_id").setValue(10);
                            myRef.child("Fields_photos").child(token).child("field_id").child("profile_url1").setValue("http://w.google.com/icture1.img");
                            myRef.child("Fields_photos").child(token).child("field_id").child("profile_url2").setValue("http://w.google.com/ictur.img");
                            myRef.child("Fields_photos").child(token).child("field_id").child("profile_url3").setValue("http://w.google.com/ictu.img");
                            myRef.child("Fields_photos").child(token).child("field_id").child("profile_url4").setValue("http://w.google.com/ict.img");


                            myRef.child("Profile").child(token).child("profile_name").setValue("ercn07");
                            myRef.child("Profile").child(token).child("profile_url").setValue("http://w.google.com/icture1.img");
                            myRef.child("Profile").child(token).child("irrigation_system").setValue("drip irrigation");

                            /*myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    String getEmail = dataSnapshot.child("Users").child(token).child("email").getValue(String.class);
                                    String name = dataSnapshot.child("Users").child(token).child("name").getValue(String.class);
                                    String surname = dataSnapshot.child("Users").child(token).child("surname").getValue(String.class);
                                    String age = dataSnapshot.child("Users").child(token).child("age").getValue(String.class);
                                    String job = dataSnapshot.child("Users").child(token).child("job").getValue(String.class);


                                    Toast.makeText(MainActivity.this, "email: " + getEmail + "\nName" + name +
                                                    "\nSurname: " + surname + "\nAge" + age +
                                                    "\nemail: " + job,
                                            Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });*/
//yoll abi


                        }
                    }
                });


    }







/*
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String getEmail=dataSnapshot.child("Users").child("-LGiGwE1S7f4micntwjP").child("email").getValue(String.class);
                String pass=dataSnapshot.child("Users").child("-LGiGwE1S7f4micntwjP").child("pass").getValue(String.class);




                Toast.makeText(MainActivity.this, ""+getEmail+"pass"+pass,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
*/

///////
    /*    myRef.child("Users").child(email).child("email").setValue(email);
        myRef.child("Users").child(email).child("pass").setValue(pass);

*/


}
