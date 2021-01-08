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
    private ServiciosClass serviciosDisponibles;
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
        obtenerServicio();
        //comprobar que la variable de servicio tenga la misma habitacion

    }

    @Override
    public void onClick(View v) {
        Intent i;
        Bundle extras= new Bundle();
        extras.putSerializable("Servicio",serviciosDisponibles);
        extras.putSerializable("habitacion",nHabitacion);

        switch (v.getId()){

            case R.id.cv_Lavanderia:
            i=new Intent(this, LavanderiaActivity.class);
            i.putExtras(extras);
            startActivity(i);
            break;

            case R.id.cv_Limpieza:
                i=new Intent(this, LimpiezaActivity.class);
                i.putExtras(extras);
                startActivity(i);
                break;

            case R.id.cv_Productos:
                i=new Intent(this, PedidosActivity.class);
                i.putExtras(extras);
                startActivity(i);
                break;

            case R.id.cv_noMolestar:
                i=new Intent(this, NomolestarActivity.class);
                i.putExtras(extras);
                startActivity(i);
                break;

            case R.id.cv_historial:
                i=new Intent(this, HistorialActivity.class);
                i.putExtras(extras);
                startActivity(i);
                break;

            case R.id.cv_problema:
                i=new Intent(this, ReporteActivity.class);
                i.putExtras(extras);
                startActivity(i);
                break;
        }


    }
    private void obtenerHabitacion(){
        ValueEventListener mDBListener;
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("habitaciones");
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String email=currentUser.getEmail();
        readData(mDatabaseRef, new NotificacionesActivity.OnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()){
                    HabitacionesClass habitacion = postSnapShot.getValue(HabitacionesClass.class);
                    habitacion.setmKey(postSnapShot.getKey());
                    if(habitacion.getCorreo().equals(email)){
                        nHabitacion=habitacion;
                        //Toast.makeText(MainActivity.this, "Habitacion Encontrada "+habitacion.getNumero(), Toast.LENGTH_SHORT).show();

                    }
                }
            }
            @Override
            public void onStart() {
                //whatever you need to do onStart
            }

            @Override
            public void onFailure() {

            }
        });
    }
    private void obtenerServicio(){

        ValueEventListener mDBListener;
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("servicios");
        readData(mDatabaseRef, new NotificacionesActivity.OnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()){
                    ServiciosClass servicio = postSnapShot.getValue(ServiciosClass.class);
                    servicio.setKey(postSnapShot.getKey());
                    if(nHabitacion.getNumero()==servicio.getHabitacion()){
                        serviciosDisponibles=servicio;
                        //Toast.makeText(MainActivity.this, "SI SE PUDO RAZA", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onStart() {
                //whatever you need to do onStart
            }

            @Override
            public void onFailure() {

            }
        });

    }

    public void logout(View v){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        finish();
    }
    public interface OnGetDataListener {
        //make new interface for call back
        void onSuccess(DataSnapshot dataSnapshot);
        void onStart();
        void onFailure();
    }
    public void readData(DatabaseReference ref, final NotificacionesActivity.OnGetDataListener listener) {
        listener.onStart();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listener.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onFailure();
            }
        });

    }
}