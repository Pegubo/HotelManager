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

public class LoginActivity extends AppCompatActivity {
    EditText Email, Password;
    Button logInButton;
    TextView RegisterTV;
    FirebaseAuth fAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Email= findViewById(R.id.username);
        Password= findViewById(R.id.password);
        logInButton= findViewById(R.id.btnIniciar);
        RegisterTV= findViewById(R.id.tvRegister);

        fAuth= FirebaseAuth.getInstance();
        progressBar= findViewById(R.id.progressBarLogin);

        logInButton.setOnClickListener(new View.OnClickListener() {
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
                fAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this,"Inicio de Sesion",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else{
                            Toast.makeText(LoginActivity.this,"Error al crear Usuario:" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        RegisterTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
            }
        });
    }
}