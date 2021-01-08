package com.example.hotelmanagerv1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PedidosActivity extends AppCompatActivity {

    private DatabaseReference Ref_pedidos;
    private DatabaseReference Ref_servicios;
    private ServiciosClass servicioDisponible;
    private EditText et_almohadas, et_toallas, et_papel, et_jabon;
    private Button btn_solicitar;
    private HabitacionesClass nHabitacion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitar_productos);

        et_almohadas = (EditText) findViewById(R.id.et_almohadas);
        et_toallas = (EditText) findViewById(R.id.et_toallas);
        et_papel = (EditText) findViewById(R.id.et_papel);
        et_jabon = (EditText) findViewById(R.id.et_jabon);

        btn_solicitar = (Button) findViewById(R.id.btn_solicitar);
        Ref_pedidos = FirebaseDatabase.getInstance().getReference("pedidos");
        Ref_servicios = FirebaseDatabase.getInstance().getReference("servicios");

        Bundle parametros = this.getIntent().getExtras();
        if (parametros != null) {
            servicioDisponible = (ServiciosClass) parametros.getSerializable("Servicio");
            nHabitacion = (HabitacionesClass) parametros.getSerializable("habitacion");

            if (servicioDisponible == null) {
                btn_solicitar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        crearServicio();
                        IraMain();
                    }
                });
            }
            else
                {
                btn_solicitar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(servicioDisponible.isNo_molestar()){
                            Map<String, Object> servicioMap= new HashMap<>();
                            servicioMap.put("no_molestar",false);
                            Ref_servicios.child(servicioDisponible.getKey()).updateChildren(servicioMap);
                            hacerPedido();
                            IraMain();
                        }
                        else{
                            hacerPedido();
                            IraMain();
                        }

                    }
                });

            }
        }
    }

    private void hacerPedido(){
        PedidosClass NuevoPedido=new PedidosClass(nHabitacion.getNumero(),Integer.parseInt(et_almohadas.getText().toString()),
                Integer.parseInt(et_toallas.getText().toString()),
                Integer.parseInt(et_papel.getText().toString()),
                Integer.parseInt(et_jabon.getText().toString()),false);


        Ref_pedidos.push().setValue(NuevoPedido).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(PedidosActivity.this, "Pedido Realizado para la habitación # "+ nHabitacion.getNumero(), Toast.LENGTH_LONG).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PedidosActivity.this, "Fallo en la creacion: "+e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void crearServicio(){
        ServiciosClass servicioDisponible=new ServiciosClass(nHabitacion.getNumero(),false,false,false);
        Ref_servicios.push().setValue(servicioDisponible).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(PedidosActivity.this, "SERVICIO HABILITADO", Toast.LENGTH_LONG).show();
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PedidosActivity.this, "Fallo en la creacion: "+e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void IraMain(){
        Intent i= new Intent(this, MainActivity.class);
        startActivity(i);
    }


}