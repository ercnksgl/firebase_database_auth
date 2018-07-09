package com.agriculture.ek.agriculture;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    DatabaseReference myRef;
    FirebaseAuth firebaseAuth;
    private String token;
    private String email;
    private String pass;

    private EditText email_edt;
    private EditText pass_edit;
    private Button register_btn;
    private TextView go_login_txt;
    private Spinner spinner;
    private String spinner_txt;
    private TextView validate_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firebaseAuth = FirebaseAuth.getInstance();

        email_edt = findViewById(R.id.register_activity_email_edt);
        pass_edit = findViewById(R.id.register_activity_pass_edt);
        register_btn = findViewById(R.id.register_Activity_register_btn);
        go_login_txt = findViewById(R.id.register_activity_login_txt);
        spinner = findViewById(R.id.register_activity_spinner_select_job);
        validate_txt = findViewById(R.id.activity_register_validate_txt);

        List<String> list = new ArrayList<>();
        list.add("Çiftçi");
        list.add("Öğrenci");
        list.add("Firma Yetkilisi");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_row, list);
        spinner.setPrompt("Ne ile meşgulsünüz?");
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.colorGreenDark));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner.setAdapter(adapter);

        go_login_txt.setClickable(true);
        go_login_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });


        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = email_edt.getText().toString().trim();
                pass = pass_edit.getText().toString().trim();

                signUp();

            }
        });


    }

    public void signUp() {
        if (pass.length() < 6) {
            Toast.makeText(this, "Şifre en az 6 karakter olmalıdır", Toast.LENGTH_SHORT).show();
        } else if (!email.contains("@") || !email.contains(".")) {
            Toast.makeText(this, "E-mailinizi kontrol ediniz", Toast.LENGTH_SHORT).show();
        } else {
            spinner_txt = spinner.getSelectedItem().toString().trim();
            firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, "Kayıt Başarılı", Toast.LENGTH_SHORT).show();

                        token = firebaseAuth.getCurrentUser().getUid();

                        myRef = FirebaseDatabase.getInstance().getReference("Services");

                        myRef.child("Users").child(token).child("email").setValue(email);
                        myRef.child("Users").child(token).child("program_job").setValue(spinner_txt);


                        final FirebaseUser user = firebaseAuth.getCurrentUser();
                        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    AlertDialog.Builder builder1 = new AlertDialog.Builder(RegisterActivity.this);
                                    builder1.setTitle("E-mail Doğrulama");
                                    builder1.setMessage("Doğrulama linki başarılı bir şekilde mailinize gönderildi.Mailinizden linki aktif edip giriş yapabilirsiniz");
                                    builder1.setCancelable(true);

                                    builder1.setPositiveButton(
                                            "Giriş Yap",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.cancel();
                                                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                                    finish();
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
                                }else{
                                    validate_txt.setText("Mail doğrularken bir hata oluştu. Lütfen tekrar deneyin.");
                                    validate_txt.setVisibility(View.VISIBLE);
                                    validate_txt.setTextColor(getResources().getColor(R.color.error_color));
                                }
                            }
                        });


/*
                        startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
                        finish();*/


                    } else {
                        Toast.makeText(RegisterActivity.this, "Bir problem ile karşılaştık. Tekrar Deneyin", Toast.LENGTH_SHORT).show();
                    }
                }


            });
        }

    }


}
