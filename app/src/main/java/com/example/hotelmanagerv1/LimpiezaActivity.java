package com.example.hotelmanagerv1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LimpiezaActivity extends AppCompatActivity {

    private DatabaseReference mDatabaseRef;
    private ServiciosClass servicioDisponible;
    private Button btn_solicitar;
    private HabitacionesClass nHabitacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cleaning);

        btn_solicitar=(Button)findViewById(R.id.btn_solicitar);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("servicios");

        Bundle parametros = this.getIntent().getExtras();
        if(parametros !=null){
            servicioDisponible=(ServiciosClass) parametros.getSerializable("Servicio");
            nHabitacion=(HabitacionesClass) parametros.getSerializable("habitacion");
            if(servicioDisponible==null){
                btn_solicitar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) { crearServicio(); }
                });

                /////////////////////////////////////////////////////////////////////

            }else{

                if(servicioDisponible.isLimpieza()) {

                    btn_solicitar.setText("Cancelar Servicio");

                    btn_solicitar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Map<String, Object> servicioMap= new HashMap<>();
                            servicioMap.put("limpieza",false);
                            servicioMap.put("lavanderia",servicioDisponible.isLavanderia());
                            servicioMap.put("no_molestar",servicioDisponible.isNo_molestar());
                            mDatabaseRef.child(servicioDisponible.getKey()).updateChildren(servicioMap);
                            btn_solicitar.setText("Solicitar Servicio");
                            v.requestLayout();
                        }
                    });
                }
                else{

                    btn_solicitar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Map<String, Object> servicioMap= new HashMap<>();
                            servicioMap.put("limpieza",true);
                            servicioMap.put("lavanderia",servicioDisponible.isLavanderia());
                            servicioMap.put("no_molestar",false);
                            mDatabaseRef.child(servicioDisponible.getKey()).updateChildren(servicioMap);
                            btn_solicitar.setText("Cancelar Servicio");
                            v.requestLayout();
                        }
                    });

                }
            }
        }

    }

    private void crearServicio() {
        ServiciosClass serviciosDisponible=new ServiciosClass(nHabitacion.getNumero(),true,false,false);

        mDatabaseRef.push().setValue(serviciosDisponible).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(LimpiezaActivity.this, "SERVICIO HABILITADO", Toast.LENGTH_LONG).show();
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LimpiezaActivity.this, "Fallo en la creacion: "+e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
