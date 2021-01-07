package com.example.hotelmanagerv1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FormularioActivity extends AppCompatActivity {
    private EditText etNumero;
    private EditText etHuesped;
    private EditText etContra;
    private EditText etfInicio;
    private EditText etfSalida;
    private CheckBox cbReservada;
    private HabitacionesClass habitacion,habagregar;
    private DatabaseReference mDatabaseRef;
    private Button btnCrear;
    private Button btnModificar;
    private Button btnEliminar;
    private ValueEventListener mDBListener;
    private FirebaseAuth fAuth;
    private List<HabitacionesClass> mHabitaciones;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("habitaciones");

        etNumero= findViewById(R.id.etNumero);
        etHuesped= findViewById(R.id.etHuesped);
        etContra= findViewById(R.id.etPass);
        etfInicio= findViewById(R.id.etfInicio);
        etfSalida= findViewById(R.id.etfSalida);
        cbReservada= findViewById(R.id.cbReservada);
        btnCrear= findViewById(R.id.btnCrear);
        btnModificar= findViewById(R.id.btnModificar);
        btnEliminar= findViewById(R.id.btnEliminar);

        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                creacionHabitacion();
            }
        });
    }

    private void creacionHabitacion() {
        habitacion= new HabitacionesClass(Integer.parseInt(etNumero.getText().toString().trim()),etContra.getText().toString().trim(),
                etHuesped.getText().toString().trim(),etfInicio.getText().toString().trim(),etfSalida.getText().toString().trim()
                ,cbReservada.isChecked());
        habitacion.setCorreo("habitacion"+habitacion.getNumero()+"@hotelmanager.com");
        mDatabaseRef.push().setValue(habitacion).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(FormularioActivity.this, "Creacion de la habitacion Exitosa", Toast.LENGTH_LONG).show();
                fAuth.createUserWithEmailAndPassword(habitacion.getCorreo().toString().trim(),habitacion.getContra().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(FormularioActivity.this,"Usuario Creado",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(FormularioActivity.this,"Error al crear Usuario:" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(FormularioActivity.this, "Fallo en la creacion: "+e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }



}