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
    private DatabaseReference ref;
    private ArrayList<String> pedidos;
    private ArrayAdapter<String> adapter;
    private PedidosClass pedido;
    private HabitacionesClass nHabitacion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

        obtenerHabitacion();
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
                    if(pedido.getHabitacion()==nHabitacion.getNumero() && pedido.isCompletado()==true) {
                        pedidos.add("Habitacion #" + Integer.toString(pedido.getHabitacion()) +
                                "    Almohadas: " + Integer.toString(pedido.getAlmohadas()) +
                                "    Toallas:   " + Integer.toString(pedido.getToallas()) +
                                "    Papel:     " + Integer.toString(pedido.getPapel()) +
                                "    Jabón      " + Integer.toString(pedido.getJabon()));
                    }
                }
                lista_pedidos.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
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
                        Toast.makeText(HistorialActivity.this, "Habitacion Encontrada "+habitacion.getNumero(), Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HistorialActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }



}