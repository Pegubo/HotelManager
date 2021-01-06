package com.example.hotelmanagerv1;

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
import java.util.List;

public class PedidosActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseRef;
    private ValueEventListener mDBListener;
    private List<PedidosClass> lst_pedidos;
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
        obtenerHabitacion();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("pedidos");
        btn_solicitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { hacerPedido(); }
        });
    }
    private void obtenerHabitacion(){
        DatabaseReference mDatabaseRef;
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
                        Toast.makeText(PedidosActivity.this, "Habitacion Encontrada "+habitacion.getNumero(), Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PedidosActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void createTable(){
        if(!lst_pedidos.isEmpty()){
            String valordefault="0";
            int i=0;
            for (PedidosClass pedidos: lst_pedidos){
                TableRow row= new TableRow(this);
                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                row.setLayoutParams(lp);
                //esto es para crear los parametros de la tabla
                TextView habitacion = new TextView(this);
                TextView almohadas= new TextView(this);
                TextView toallas= new TextView(this);
                TextView papel= new TextView(this);
                TextView jabon= new TextView(this);
                CheckBox completado=new CheckBox(this);


                habitacion.setText(nHabitacion.getNumero());
                almohadas.setText(et_almohadas.getText());
                toallas.setText(et_toallas.getText());
                papel.setText(et_papel.getText());
                jabon.setText(et_jabon.getText());
                completado.setChecked(false);

                row.addView(almohadas);
                row.addView(toallas);
                row.addView(papel);
                row.addView(jabon);
                row.addView(completado);

            }
        }
        else{
            Toast.makeText(this, "No se pudo realizar el pedido", Toast.LENGTH_SHORT).show();
        }
    }

    private void hacerPedido(){
        PedidosClass NuevoPedido=new PedidosClass(nHabitacion.getNumero(),Integer.parseInt(et_almohadas.getText().toString()),
                Integer.parseInt(et_toallas.getText().toString()),
                Integer.parseInt(et_papel.getText().toString()),
                Integer.parseInt(et_jabon.getText().toString()),false);

        mDatabaseRef.push().setValue(NuevoPedido).addOnSuccessListener(new OnSuccessListener<Void>() {
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

}