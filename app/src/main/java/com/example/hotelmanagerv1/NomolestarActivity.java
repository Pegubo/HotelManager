package com.example.hotelmanagerv1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class NomolestarActivity extends AppCompatActivity {

    private DatabaseReference mDatabaseRef;
    private ServiciosClass servicioDisponible;
    private Button btn_solicitar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_molestar);

        btn_solicitar=(Button)findViewById(R.id.btn_solicitar);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("servicios");

        Bundle parametros = this.getIntent().getExtras();
        if(parametros !=null){
            servicioDisponible=(ServiciosClass) parametros.getSerializable("Servicio");

            if(servicioDisponible==null){
                btn_solicitar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) { crearServicio(); }
                });

                /////////////////////////////////////////////////////////////////////

            }else{

                if(servicioDisponible.isNo_molestar()) {

                    btn_solicitar.setText("Cancelar Servicio");

                    btn_solicitar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Map<String, Object> servicioMap= new HashMap<>();
                            servicioMap.put("no_molestar",false);
                            servicioMap.put("limpieza",servicioDisponible.isLimpieza());
                            servicioMap.put("lavanderia",servicioDisponible.isLavanderia());
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
                            servicioMap.put("no_molestar",true);
                            servicioMap.put("limpieza",false);
                            servicioMap.put("lavanderia",false);
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
        ServiciosClass serviciosDisponible=new ServiciosClass(servicioDisponible.getHabitacion(),false,false,true);

        mDatabaseRef.push().setValue(serviciosDisponible).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(NomolestarActivity.this, "SERVICIO HABILITADO", Toast.LENGTH_LONG).show();
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(NomolestarActivity.this, "Fallo en la creacion: "+e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}