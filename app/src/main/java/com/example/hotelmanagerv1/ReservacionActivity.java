package com.example.hotelmanagerv1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReservacionActivity extends AppCompatActivity {
    private EditText etNumero;
    private EditText etHuesped;
    private EditText etContra;
    private EditText etfInicio;
    private EditText etfSalida;
    private CheckBox cbReservada;
    private HabitacionesClass habitacion,habagregar;
    private DatabaseReference mDatabaseRef;
    private Button btnModificar;
    private Button btnEliminar;
    private ValueEventListener mDBListener;
    private FirebaseAuth fAuth;
    private List<HabitacionesClass> mHabitaciones;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservacion);

        etNumero= findViewById(R.id.etNumero);
        etHuesped= findViewById(R.id.etHuesped);
        etContra= findViewById(R.id.etPass);
        etfInicio= findViewById(R.id.etfInicio);
        etfSalida= findViewById(R.id.etfSalida);
        cbReservada= findViewById(R.id.cbReservada);
        btnModificar= findViewById(R.id.btnModificar);
        btnEliminar= findViewById(R.id.btnEliminar);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("habitaciones");
        mHabitaciones= new ArrayList<>();

        Bundle parametros = this.getIntent().getExtras();
        if(parametros !=null){
            String datos = parametros.getString("Opcion");
            habagregar= (HabitacionesClass) parametros.getSerializable("habitacion");
            if(datos.equals("Agregar")){
                btnModificar.setVisibility(View.VISIBLE);
                Toast.makeText(this, habagregar.getCorreo().toString(), Toast.LENGTH_SHORT).show();
                etNumero.setText("Habitacion "+habagregar.getNumero());
                etNumero.setEnabled(false);
                etContra.setText(habagregar.getContra().toString().trim());
                etContra.setEnabled(false);
                btnModificar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String huesped= etHuesped.toString().trim();
                        String fInicio= etfInicio.toString().trim();
                        String fSalida= etfSalida.toString().trim();
                        if(TextUtils.isEmpty(huesped)){
                            etHuesped.setError("Ingrese el Huesped.");
                            return;
                        }
                        if(TextUtils.isEmpty(fInicio)){
                            etfInicio.setError("Ingrese la Fecha de Inicio.");
                            return;
                        }
                        if(TextUtils.isEmpty(fSalida)){
                            etHuesped.setError("Ingrese la Fecha de Salida.");
                            return;
                        }
                        Map<String, Object> habitacionMap= new HashMap<>();
                        habitacionMap.put("contra",habagregar.getContra().toString());
                        habitacionMap.put("correo",habagregar.getCorreo().toString());
                        habitacionMap.put("fInicio",fInicio);
                        habitacionMap.put("fSalida",fSalida);
                        habitacionMap.put("huesped",huesped);
                        habitacionMap.put("numero",habagregar.getNumero());
                        habitacionMap.put("reservada",true);
                        mDatabaseRef.child(habagregar.getmKey()).updateChildren(habitacionMap);
                    }
                });
            }
            if (datos.equals("Eliminar")){
                btnEliminar.setVisibility(View.VISIBLE);
                etNumero.setText("Habitacion "+habagregar.getNumero());
                etNumero.setEnabled(false);
                etHuesped.setText(habagregar.getHuesped().toString().trim());
                etHuesped.setEnabled(false);
                etContra.setText(habagregar.getContra().toString().trim());
                etContra.setEnabled(false);
                etfInicio.setText(habagregar.getfInicio().toString().trim());
                etfInicio.setEnabled(false);
                etfSalida.setText(habagregar.getfSalida().toString().trim());
                etfSalida.setEnabled(false);
                cbReservada.setChecked(true);
                cbReservada.setEnabled(false);
                btnEliminar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String, Object> habitacionMap= new HashMap<>();
                        habitacionMap.put("contra",habagregar.getContra().toString());
                        habitacionMap.put("correo",habagregar.getCorreo().toString());
                        habitacionMap.put("fInicio","");
                        habitacionMap.put("fSalida","");
                        habitacionMap.put("huesped","");
                        habitacionMap.put("numero",habagregar.getNumero());
                        habitacionMap.put("reservada",false);
                        mDatabaseRef.child(habagregar.getmKey()).updateChildren(habitacionMap);
                    }
                });
            }
        }
    }

}