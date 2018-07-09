package com.agriculture.ek.agriculture;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference myRef;
    FirebaseAuth firebaseAuth;
    private String token;
    private String email;
    private String pass;

    private EditText email_edt;
    private EditText pass_edit;
    private Button register_btn;
    private TextView go_register_txt;
    private TextView forgot_pasword_txt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();

        email_edt = findViewById(R.id.login_activity_email_edt);
        pass_edit = findViewById(R.id.login_activity_pass_edt);
        register_btn = findViewById(R.id.login_activity_login_btn);
        go_register_txt = findViewById(R.id.login_activity_register_txt);
        forgot_pasword_txt = findViewById(R.id.login_activity_forgot_password_txt);
        forgot_pasword_txt.setClickable(true);
        forgot_pasword_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(LoginActivity.this);
                builder1.setTitle("Şifre Sıfırla(E-mail ile)");
                builder1.setMessage("Şifrenizi sıfırlamak istiyor musunuz?  (Şifre değiştirmek için mailinizi kontol etmelisiniz)");
                builder1.setCancelable(true);
                final EditText input = new EditText(v.getContext());
                builder1.setView(input);
                input.setTextColor(getResources().getColor(R.color.colorGreenDark));
                input.setHintTextColor(getResources().getColor(R.color.text_hint_color));
                input.setTextSize(16);
                input.setTypeface(Typeface.DEFAULT_BOLD);
                input.setHint("E-mail");

                builder1.setPositiveButton(
                        "Evet, Sıfırla",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (input.getText().length() < 8 || !input.getText().toString().contains("@") || !input.getText().toString().contains(".")) {
                                    Toast.makeText(LoginActivity.this, "Girdiğiniz E-mail eksik veya hatalı", Toast.LENGTH_SHORT).show();
                                    dialog.cancel();
                                } else {
                                    FirebaseAuth.getInstance().sendPasswordResetEmail(input.getText().toString().trim())
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(LoginActivity.this, "Şifre değiştirmek için mailinizi kontrol ediniz", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        Toast.makeText(LoginActivity.this, "Bir hata oluştu. Lütfen Tekrar deneyiniz.", Toast.LENGTH_SHORT).show();

                                                    }
                                                }
                                            });
                                }

                            }
                        });

                builder1.setNegativeButton(
                        "İptal",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });

        go_register_txt.setClickable(true);
        go_register_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });


        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = email_edt.getText().toString().trim();
                pass = pass_edit.getText().toString().trim();

                signIn();

            }
        });


    }


    public void signIn() {


        firebaseAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if (!firebaseAuth.getCurrentUser().isEmailVerified()) {

                                firebaseAuth.getCurrentUser().reload();
                                Toast.makeText(LoginActivity.this, "Email Doğrulanmadı", Toast.LENGTH_SHORT).show();

                            } else {

                                token = firebaseAuth.getCurrentUser().getUid();
                                myRef = FirebaseDatabase.getInstance().getReference("Services");

                                myRef.child("Users").child(token).child("email").setValue(email);
                                myRef.child("Users").child(token).child("name").setValue("Ercan");
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
                                myRef.child("Profile").child(token).child("irrigation_system").setValue("Damla sulama");


                                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                finish();

                            }
                        }
                    }
                });


    }

}
