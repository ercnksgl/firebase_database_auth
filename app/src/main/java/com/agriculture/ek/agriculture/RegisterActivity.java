package com.agriculture.ek.agriculture;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import org.w3c.dom.Text;

public class RegisterActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference myRef;
    FirebaseAuth firebaseAuth;
    private String token;
    private String email;
    private String pass;

    private EditText email_edt;
    private EditText pass_edit;
    private Button register_btn;
    private TextView go_login_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firebaseAuth = FirebaseAuth.getInstance();

        email_edt=findViewById(R.id.register_activity_email_edt);
        pass_edit=findViewById(R.id.register_activity_pass_edt);
        register_btn=findViewById(R.id.register_Activity_register_btn);
        go_login_txt=findViewById(R.id.register_activity_login_txt);
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

    public void signUp(){
        firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "it's okay!You registered!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this,HomeActivity.class));
                    finish();

                } else {
                    Toast.makeText(RegisterActivity.this, "problemu!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}
