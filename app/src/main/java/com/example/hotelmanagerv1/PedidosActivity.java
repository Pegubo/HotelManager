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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PedidosActivity extends AppCompatActivity {

    private DatabaseReference mDatabaseRef;
    private ValueEventListener mDBListener;
    private List<PedidosClass> lst_pedidos;
    private EditText et_almohadas, et_toallas, et_papel, et_jabon;
    private Button btn_solicitar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitar_productos);

        et_almohadas=(EditText)findViewById(R.id.et_almohadas);
        et_toallas=(EditText)findViewById(R.id.et_toallas);
        et_papel=(EditText)findViewById(R.id.et_papel);
        et_jabon=(EditText)findViewById(R.id.et_jabon);

        btn_solicitar=(Button)findViewById(R.id.btn_solicitar);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("pedidos");
        lst_pedidos= new ArrayList<>();

        mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot postSnapShot : snapshot.getChildren()) {
                    PedidosClass pedidos = postSnapShot.getValue(PedidosClass.class);
                    pedidos.setKey(postSnapShot.getKey());
                    lst_pedidos.add(pedidos);
                }
                //createTable();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        private void createTable(){
            if(!lst_pedidos.isEmpty()){
                int i=0;
                for (PedidosClass pedidos: lst_pedidos){
                    TableRow row= new TableRow(this);
                    TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                    row.setLayoutParams(lp);
                    //esto es para crear los parametros de la tabla?
                    TextView pos = new TextView(this);
                    TextView numero = new TextView(this);
                    CheckBox Reservada= new CheckBox(this);
                    Button btnSelect = new Button(this);

                    //esto es para llenar los parametros anteriores?
                    //pos.setText(i);
                    numero.setText("Habitacion "+habitacion.getNumero());
                    Reservada.setText("Reservada");
                    Reservada.setChecked(habitacion.getReservada());
                    Reservada.setEnabled(false);
                    if(habitacion.getReservada()==true){
                        btnSelect.setText("Terminar");
                        btnSelect.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(HabitacionActivity.this, "Terminar", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else{
                        btnSelect.setText("Agregar Reservacion");
                        btnSelect.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(HabitacionActivity.this, "Agregar", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    //row.addView(pos);
                    row.addView(numero);
                    row.addView(Reservada);
                    row.addView(btnSelect);

                    tHabitaciones.addView(row,i);
                    i++;
                }
            }
            else{
                Toast.makeText(this, "No hay habitaciones disponibles para mostrar", Toast.LENGTH_SHORT).show();
            }

    }
}