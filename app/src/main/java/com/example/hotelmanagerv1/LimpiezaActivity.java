package com.example.hotelmanagerv1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LimpiezaActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseRef;
    private ValueEventListener mDBListener;
    private Button btn_solicitar;
    private HabitacionesClass nHabitacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cleaning);
        obtenerHabitacion();

        //mDatabaseRef = FirebaseDatabase.getInstance().getReference("servicios");
       /* btn_solicitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { solicitarServicio(); }
        });*/

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
                        Toast.makeText(LimpiezaActivity.this, "Habitacion Encontrada "+habitacion.getNumero(), Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(LimpiezaActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void solicitarServicio(){

    }

}