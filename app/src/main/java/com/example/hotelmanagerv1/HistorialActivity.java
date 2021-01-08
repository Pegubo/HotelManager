package com.example.hotelmanagerv1;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class HistorialActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private ListView lista_pedidos;
    private FirebaseDatabase database;
    private DatabaseReference Ref_pedidos;
    private DatabaseReference Ref_servicios;
    private ArrayList<String> pedidos;
    private ArrayAdapter<String> adapter;
    private PedidosClass pedido;
    private ServiciosClass servicioDisponible;
    private HabitacionesClass nHabitacion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

        pedido=new PedidosClass();
        lista_pedidos=(ListView)findViewById(R.id.lista_pedidos);
        pedidos=new ArrayList<>();
        adapter=new ArrayAdapter<>(HistorialActivity.this,android.R.layout.simple_list_item_1,pedidos);


        Ref_servicios = FirebaseDatabase.getInstance().getReference("servicios");
        Ref_pedidos = FirebaseDatabase.getInstance().getReference("pedidos");

        Bundle parametros = this.getIntent().getExtras();
        if (parametros != null) {
            servicioDisponible = (ServiciosClass) parametros.getSerializable("Servicio");
            nHabitacion = (HabitacionesClass) parametros.getSerializable("habitacion");


            Ref_pedidos.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot postSnapShot : snapshot.getChildren()) {
                        pedido = postSnapShot.getValue(PedidosClass.class);
                        if (pedido.getHabitacion() == nHabitacion.getNumero() && pedido.isCompletado() == true) {
                            pedidos.add("Habitacion #" + Integer.toString(pedido.getHabitacion()) +
                                    " Almohadas: " + Integer.toString(pedido.getAlmohadas()) +
                                    " Toallas:   " + Integer.toString(pedido.getToallas()) +
                                    " Papel:     " + Integer.toString(pedido.getPapel()) +
                                    " Jabón      " + Integer.toString(pedido.getJabon()));
                        }
                    }
                    lista_pedidos.setAdapter(adapter);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
        else{
            Toast.makeText(HistorialActivity.this, "No se han realizado pedidos a la habitacion #"+ nHabitacion.getNumero(), Toast.LENGTH_LONG).show();

        }

    }

}