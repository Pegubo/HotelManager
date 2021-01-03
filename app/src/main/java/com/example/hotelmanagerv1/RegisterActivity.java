package com.example.hotelmanagerv1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    EditText FullName, Email, Password, Phone;
    Button RegisterButton;
    TextView Loginbtn;
    FirebaseAuth fAuth;
    DatabaseReference mDatabaseRef;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        FullName= findViewById(R.id.ETname);
        Email= findViewById(R.id.ETmail);
        Password= findViewById(R.id.ETpass);
        Phone= findViewById(R.id.ETphone);
        RegisterButton= findViewById(R.id.btnRegistro);
        Loginbtn= findViewById(R.id.loginBtn);

        fAuth= FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("users");
        progressBar= findViewById(R.id.progressBar);

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email= Email.getText().toString().trim();
                String pass= Password.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    Email.setError("Ingrese el correo porfavor.");
                    return;
                }
                if(TextUtils.isEmpty(pass)){
                    Password.setError("La contraseña es requerida");
                    return;
                }

                if(pass.length()<6){
                    Password.setError("Contraseña debe de ser mayor a 6 caracteres");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                //Registrar al usuario
                fAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this,"Usuario Creado",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else{
                            Toast.makeText(RegisterActivity.this,"Error al crear Usuario:" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });

        Loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });
    }

}