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
    private HabitacionesClass habitacionActual;
    private EditText et_almohadas, et_toallas, et_papel, et_jabon;
    private Button btn_solicitar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitar_productos);

        et_almohadas = (EditText) findViewById(R.id.et_almohadas);
        et_toallas = (EditText) findViewById(R.id.et_toallas);
        et_papel = (EditText) findViewById(R.id.et_papel);
        et_jabon = (EditText) findViewById(R.id.et_jabon);

        btn_solicitar = (Button) findViewById(R.id.btn_solicitar);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("pedidos");
        lst_pedidos = new ArrayList<>();

        mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot postSnapShot : snapshot.getChildren()) {
                    PedidosClass pedidos = postSnapShot.getValue(PedidosClass.class);
                    pedidos.setKey(postSnapShot.getKey());
                    lst_pedidos.add(pedidos);
                }
                createTable();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

        private void createTable(){
            if(!lst_pedidos.isEmpty()){
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


                    //esto es para llenar los parametros anteriores
                    //pos.setText(i);
                    //habitacion.setText("Habitacion "+habitacion.getNumero()); obtener la habitacion desde la base de datos

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
}