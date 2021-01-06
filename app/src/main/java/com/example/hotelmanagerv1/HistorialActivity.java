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

    ListView lista_pedidos;
    FirebaseDatabase database;
    DatabaseReference ref;
    ArrayList<PedidosClass> pedidos;
    ArrayAdapter<PedidosClass> adapter;
    PedidosClass pedido;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

        pedido=new PedidosClass();
        lista_pedidos=(ListView)findViewById(R.id.lista_pedidos);
        pedidos=new ArrayList<>();
        adapter=new ArrayAdapter<>(HistorialActivity.this,android.R.layout.simple_list_item_1,pedidos);
        database=FirebaseDatabase.getInstance();
        ref=database.getReference("pedidos");


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapShot : snapshot.getChildren()){
                    pedido=postSnapShot.getValue(PedidosClass.class);
                    pedidos.add(pedido);
                }
                lista_pedidos.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }



}