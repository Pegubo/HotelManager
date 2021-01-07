package com.example.hotelmanagerv1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseRef;
    private ValueEventListener mDBListener;
    private HabitacionesClass nHabitacion;
    public CardView lavadora,limpieza,productos,nomolestar,historial,problema;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lavadora = (CardView) findViewById(R.id.cv_Lavanderia);
        limpieza = (CardView) findViewById(R.id.cv_Limpieza);
        productos = (CardView) findViewById(R.id.cv_Productos);
        nomolestar = (CardView) findViewById(R.id.cv_noMolestar);
        historial = (CardView) findViewById(R.id.cv_historial);
        problema = (CardView) findViewById(R.id.cv_problema);

        lavadora.setOnClickListener(this);
        limpieza.setOnClickListener(this);
        productos.setOnClickListener(this);
        nomolestar.setOnClickListener(this);
        historial.setOnClickListener(this);
        problema.setOnClickListener(this);

        obtenerHabitacion();

    }

    @Override
    public void onClick(View v) {
        Intent i;

        switch (v.getId()){

            case R.id.cv_Lavanderia:
            i=new Intent(this, LavanderiaActivity.class);
            startActivity(i);
            break;

            case R.id.cv_Limpieza:
                i=new Intent(this, LimpiezaActivity.class);
                startActivity(i);
                break;

            case R.id.cv_Productos:
                i=new Intent(this, PedidosActivity.class);
                startActivity(i);
                break;

            case R.id.cv_noMolestar:
                i=new Intent(this, NomolestarActivity.class);
                startActivity(i);
                break;

            case R.id.cv_historial:
                i=new Intent(this, HistorialActivity.class);
                startActivity(i);
                break;

            case R.id.cv_problema:
                i=new Intent(this, ReporteActivity.class);
                startActivity(i);
                break;
        }


    }

    private void obtenerHabitacion(){

        ValueEventListener mDBListener;
        List<HabitacionesClass> mHabitaciones;
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("habitaciones");
        mHabitaciones= new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String email=currentUser.getEmail();
        mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot postSnapShot : snapshot.getChildren()){
                    HabitacionesClass habitacion = postSnapShot.getValue(HabitacionesClass.class);
                    habitacion.setmKey(postSnapShot.getKey());
                    if(habitacion.getCorreo().equals(email)){
                        nHabitacion=habitacion;
                        Toast.makeText(MainActivity.this, "Habitacion Encontrada "+habitacion.getNumero(), Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void logout(View v){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        finish();
    }
}