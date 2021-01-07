package com.example.hotelmanagerv1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HabitacionActivity extends AppCompatActivity {
    private TableLayout tHabitaciones;
    private DatabaseReference mDatabaseRef;
    private ValueEventListener mDBListener;
    private List<HabitacionesClass> mHabitaciones;
    private Button btnHabitacion;
    private FirebaseAuth mAuth;
    private HabitacionesClass nHabitacion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habitacion);

        tHabitaciones= findViewById(R.id.tabla_servicios);
        btnHabitacion= findViewById(R.id.btnHabitacion);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("habitaciones");
        mHabitaciones= new ArrayList<>();

        btnHabitacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFormularioActivity();
            }
        });

        mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mHabitaciones.clear();
                for (DataSnapshot postSnapShot : snapshot.getChildren()){
                    HabitacionesClass habitacion = postSnapShot.getValue(HabitacionesClass.class);
                    habitacion.setmKey(postSnapShot.getKey());
                    mHabitaciones.add(habitacion);
                }
                createTable();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HabitacionActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void openFormularioActivity() {
        Intent intent= new Intent(this, FormularioActivity.class);
        startActivity(intent);
    }

    private void createTable(){
        if(!mHabitaciones.isEmpty()){
            int i=0;
            for (HabitacionesClass habitacion: mHabitaciones){
                TableRow row= new TableRow(this);
                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                row.setLayoutParams(lp);
                TextView pos = new TextView(this);
                TextView numero = new TextView(this);
                CheckBox Reservada= new CheckBox(this);
                Button btnSelect = new Button(this);

                //pos.setText(i);
                numero.setText("Habitacion "+habitacion.getNumero());
                Reservada.setText("Reservada");
                Reservada.setChecked(habitacion.getReservada());
                Reservada.setEnabled(false);
                if(habitacion.getReservada()==true){
                    btnSelect.setText("Terminar");
                    btnSelect.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(HabitacionActivity.this, "Terminar", Toast.LENGTH_SHORT).show();
                            terminarReservacion(habitacion);
                        }
                    });
                }
                else{
                    btnSelect.setText("Agregar Reservacion");
                    btnSelect.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(HabitacionActivity.this, "Agregar", Toast.LENGTH_SHORT).show();

                            agregarReservacion(habitacion);
                            //mDatabaseRef.child(habitacion.getmKey()).updateChildren();
                        }
                    });
                }

                //row.addView(pos);
                row.addView(numero);
                row.addView(Reservada);
                row.addView(btnSelect);

                tHabitaciones.addView(row,i);
                i++;
            }
        }
        else{
            Toast.makeText(this, "No hay habitaciones disponibles para mostrar", Toast.LENGTH_SHORT).show();
        }
    }

    private void agregarReservacion(HabitacionesClass habitacion) {
        Bundle extras= new Bundle();
        extras.putString("Opcion","Agregar");
        extras.putSerializable("habitacion",habitacion);
        Intent intent= new Intent(this, ReservacionActivity.class);
        intent.putExtras(extras);
        startActivity(intent);
    }

    private void terminarReservacion(HabitacionesClass habitacion) {
        Bundle extras= new Bundle();
        extras.putString("Opcion","Eliminar");
        extras.putSerializable("habitacion",habitacion);
        Intent intent= new Intent(this, ReservacionActivity.class);
        intent.putExtras(extras);
        startActivity(intent);
    }
}