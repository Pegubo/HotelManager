package com.example.hotelmanagerv1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ReporteActivity extends AppCompatActivity {

    private DatabaseReference Ref_problemas;
    private DatabaseReference Ref_servicios;
    private HabitacionesClass nHabitacion;
    private ServiciosClass servicioDisponible;
    private EditText problema_reportado;
    private Button btn_Enviar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problemas);

        problema_reportado = (EditText) findViewById(R.id.problema_reportado);
        btn_Enviar = (Button) findViewById(R.id.btn_Enviar);

        Ref_problemas = FirebaseDatabase.getInstance().getReference("problemas");

        Bundle parametros = this.getIntent().getExtras();
        if (parametros != null) {
            servicioDisponible = (ServiciosClass) parametros.getSerializable("Servicio");
            nHabitacion = (HabitacionesClass) parametros.getSerializable("habitacion");

            if (servicioDisponible == null) {
                btn_Enviar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        crearServicio();
                        IraMain();
                    }
                });
            }
            else
            {
                btn_Enviar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        crearReporte();
                        IraMain();
                    }
                });

            }
        }

    }

    private void crearReporte(){
            ReporteClass nuevo_reporte=new ReporteClass(nHabitacion.getNumero(),problema_reportado.getText().toString());
            Ref_problemas.push().setValue(nuevo_reporte).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(ReporteActivity.this, "Problema Reportado en la habitación # "+ nHabitacion.getNumero(), Toast.LENGTH_LONG).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ReporteActivity.this, "Fallo en la creacion: "+e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void crearServicio(){
        ServiciosClass servicioDisponible=new ServiciosClass(nHabitacion.getNumero(),false,false,false);
        Ref_servicios.push().setValue(servicioDisponible).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(ReporteActivity.this, "SERVICIO HABILITADO", Toast.LENGTH_LONG).show();
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ReporteActivity.this, "Fallo en la creacion: "+e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void IraMain(){
        Intent i= new Intent(this, MainActivity.class);
        startActivity(i);
    }

}