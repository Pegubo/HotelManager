package com.example.hotelmanagerv1;

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

public class LavanderiaActivity extends AppCompatActivity {


    private DatabaseReference mDatabaseRef;
    private ServiciosClass servicioDisponible;
    private Button btn_solicitar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laundry);

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
            }else{
                if(servicioDisponible.isLavanderia()){
                    btn_solicitar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) { cancelarServicio(); }
                    });
                }
            }
        }

    }

    private void crearServicio() {
        ServiciosClass servicioDisponible=new ServiciosClass(1,false,true,false);

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

    private void cancelarServicio(){
        
    }

}
