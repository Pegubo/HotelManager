package com.example.hotelmanagerv1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class LavanderiaActivity extends AppCompatActivity {

    private DatabaseReference mDatabaseRef;
    private ServiciosClass servicioDisponible;
    private Button btn_solicitar;
    private HabitacionesClass nHabitacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laundry);

        btn_solicitar=(Button)findViewById(R.id.btn_solicitar);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("servicios");

        Bundle parametros = this.getIntent().getExtras();
        if(parametros !=null){
            servicioDisponible=(ServiciosClass) parametros.getSerializable("Servicio");
            nHabitacion=(HabitacionesClass) parametros.getSerializable("habitacion");
            if(servicioDisponible==null){
                btn_solicitar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        crearServicio();
                        IraMain();
                    }
                });

            }else{

                if(servicioDisponible.isLavanderia()) {

                    btn_solicitar.setText("Cancelar Servicio");

                    btn_solicitar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Map<String, Object> servicioMap= new HashMap<>();
                            servicioMap.put("lavanderia",false);
                            mDatabaseRef.child(servicioDisponible.getKey()).updateChildren(servicioMap);
                            btn_solicitar.setText("Solicitar Servicio");
                            IraMain();

                        }
                    });
                }
                else{

                    btn_solicitar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Map<String, Object> servicioMap= new HashMap<>();
                            servicioMap.put("lavanderia",true);
                            servicioMap.put("no_molestar",false);
                            mDatabaseRef.child(servicioDisponible.getKey()).updateChildren(servicioMap);
                            btn_solicitar.setText("Cancelar Servicio");
                            IraMain();
                        }
                    });

                }
            }
        }

    }

    private void crearServicio() {
        ServiciosClass servicioDisponible=new ServiciosClass(nHabitacion.getNumero(),false,true,false);
        mDatabaseRef.push().setValue(servicioDisponible).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(LavanderiaActivity.this, "SERVICIO HABILITADO", Toast.LENGTH_LONG).show();
                }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LavanderiaActivity.this, "Fallo en la creacion: "+e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void IraMain(){
        Intent i= new Intent(this, MainActivity.class);
        startActivity(i);
    }

}
