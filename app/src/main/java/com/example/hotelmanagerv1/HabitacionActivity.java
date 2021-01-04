package com.example.hotelmanagerv1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class HabitacionActivity extends AppCompatActivity {
    private TableLayout tHabitaciones;
    private DatabaseReference mDatabaseRef;
    private ValueEventListener mDBListener;
    private List<HabitacionesClass> mHabitaciones;
    private Button btnHabitacion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habitacion);

        tHabitaciones= findViewById(R.id.tabla_habitaciones);
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

                for (DataSnapshot postSnapShot : snapshot.getChildren()){
                    HabitacionesClass habitacion = postSnapShot.getValue(HabitacionesClass.class);
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
                TextView numero = new TextView(this);
                TextView contra = new TextView(this);
                TextView huesped = new TextView(this);
                TextView fSalida = new TextView(this);
                TextView fInicio = new TextView(this);
                CheckBox Reservada= new CheckBox(this);
                Button btnSelect = new Button(this);


                numero.setText("Habitacion"+habitacion.getNumero());
                contra.setText(habitacion.getContra());
                huesped.setText(habitacion.getHuesped());
                Reservada.setText("Reservada");
                Reservada.setChecked(habitacion.getReservada());
                if(habitacion.getReservada()==true){
                    fSalida.setText(habitacion.getfSalida());
                    fInicio.setText(habitacion.getfInicio());
                    btnSelect.setText("Terminar");
                }
                else{
                    fSalida.setText(habitacion.getfSalida());
                    fInicio.setText(habitacion.getfInicio());
                    btnSelect.setText("Agregar Reservacion");
                }


                row.addView(numero);
                row.addView(contra);
                row.addView(huesped);
                row.addView(fInicio);
                row.addView(fSalida);
                row.addView(btnSelect);
                row.addView(Reservada);
                tHabitaciones.addView(row,i);
                i++;
            }
        }
        else{
            Toast.makeText(this, "No hay habitaciones disponibles para mostrar", Toast.LENGTH_SHORT).show();
        }
    }
}