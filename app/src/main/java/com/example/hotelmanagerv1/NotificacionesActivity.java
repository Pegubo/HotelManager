package com.example.hotelmanagerv1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificacionesActivity extends AppCompatActivity {
    private TableLayout tServicios, tPedidos;
    private DatabaseReference mDatabaseRefPed;
    private DatabaseReference mDatabaseRefSer;
    private ValueEventListener mDBListener;
    private List<ServiciosClass> mServicios;
    private List<PedidosClass> mPedidos;
    private ServiciosClass nServicio;
    private PedidosClass nPedidos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificaciones);

        tServicios= findViewById(R.id.tabla_servicios);
        tPedidos= findViewById(R.id.tabla_pedidos);

        ///////////Servicios///////////
        mDatabaseRefSer = FirebaseDatabase.getInstance().getReference("servicios");
        mServicios= new ArrayList<>();

        readData(mDatabaseRefSer, new OnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                mServicios.clear();
                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()){
                    ServiciosClass serv = postSnapShot.getValue(ServiciosClass.class);
                    serv.setKey(postSnapShot.getKey());
                    mServicios.add(serv);
                }
                createTableServicios();
            }
            @Override
            public void onStart() {
                //whatever you need to do onStart
            }

            @Override
            public void onFailure() {

            }
        });
/////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////Pedidos////////////
        mDatabaseRefPed = FirebaseDatabase.getInstance().getReference("pedidos");
        mPedidos= new ArrayList<>();

        readData(mDatabaseRefPed, new OnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                mPedidos.clear();
                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()){
                    PedidosClass ped = postSnapShot.getValue(PedidosClass.class);
                    ped.setKey(postSnapShot.getKey());
                    mPedidos.add(ped);
                }
                createTablePedidos();
            }
            @Override
            public void onStart() {
                //whatever you need to do onStart
            }

            @Override
            public void onFailure() {

            }
        });
    }
    private void createTableServicios(){
        if(!mServicios.isEmpty()){
            int i=0;
            for (ServiciosClass serv: mServicios){
                TableRow row= new TableRow(this);
                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                row.setLayoutParams(lp);

                Button lavanderia=new Button(this);
                Button limpieza=new Button(this);
                CheckBox molestar=new CheckBox(this);
                TextView habitacion= new TextView(this);

                habitacion.setText("Habitacion "+serv.getHabitacion());
                if (serv.isNo_molestar()){
                    molestar.setChecked(serv.isNo_molestar());
                    molestar.setEnabled(false);
                    lavanderia.setText("No");
                    limpieza.setText("Molestar");

                }
                else{
                    if(serv.isLavanderia()){
                        lavanderia.setText("Si");
                        lavanderia.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Map<String,Object> lavanderiaMap= new HashMap<>();
                                lavanderiaMap.put("lavanderia",false);
                                mDatabaseRefSer.child(serv.getKey()).updateChildren(lavanderiaMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(NotificacionesActivity.this, "Lavanderia Terminada", Toast.LENGTH_SHORT).show();
                                        Refrescar();
                                    }
                                });
                            }
                        });
                    }
                    else{
                        lavanderia.setText("No");
                        lavanderia.setEnabled(false);
                    }
                    if(serv.isLimpieza()){
                        limpieza.setText("Si");
                        limpieza.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Map<String,Object> limpiezaMap= new HashMap<>();
                                limpiezaMap.put("limpieza",false);
                                mDatabaseRefSer.child(serv.getKey()).updateChildren(limpiezaMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(NotificacionesActivity.this, "Limpieza Terminada", Toast.LENGTH_SHORT).show();
                                        Refrescar();
                                    }
                                });
                            }
                        });
                    }
                    else{
                        limpieza.setText("No");
                        limpieza.setEnabled(false);
                    }
                    molestar.setChecked(serv.isNo_molestar());
                    molestar.setEnabled(false);
                }

                row.addView(habitacion);
                row.addView(lavanderia);
                row.addView(limpieza);
                row.addView(molestar);

                tServicios.addView(row,i);
                i++;
            }
            TableRow rowheader= new TableRow(this);
            TableRow.LayoutParams lph = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            rowheader.setLayoutParams(lph);
            //header
            TextView hab=new TextView(this);
            TextView lav=new TextView(this);
            TextView lim=new TextView(this);
            TextView mol=new TextView(this);

            hab.setText("Habitacion");
            lav.setText(" Lavanderia");
            lim.setText(" Limpieza");
            mol.setText(" No Molestar");

            rowheader.addView(hab);
            rowheader.addView(lav);
            rowheader.addView(lim);
            rowheader.addView(mol);
            tServicios.addView(rowheader,0);
        }
        else{
            Toast.makeText(this, "No hay habitaciones disponibles para mostrar", Toast.LENGTH_SHORT).show();
        }
    }
    private void createTablePedidos(){
        if(!mPedidos.isEmpty()){
            int i=0;
            for (PedidosClass ped: mPedidos){
                TableRow row= new TableRow(this);
                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                row.setLayoutParams(lp);
                TextView habitacion = new TextView(this);
                TextView almohadas = new TextView(this);
                TextView jabon = new TextView(this);
                TextView papel = new TextView(this);
                TextView toallas = new TextView(this);
                CheckBox completado= new CheckBox(this);
                Button btnSelect = new Button(this);

                //pos.setText(i);
                habitacion.setText("Habitacion "+ped.getHabitacion());
                almohadas.setText("Almohadas: "+ped.getAlmohadas());
                almohadas.setEnabled(false);
                jabon.setText("Jabon: "+ped.getJabon());
                jabon.setEnabled(false);
                papel.setText("Papel: "+ped.getPapel());
                papel.setEnabled(false);
                toallas.setText("Toallas: "+ped.getToallas());
                toallas.setEnabled(false);
                //completado.setText("Completado");
                completado.setText("Estado");
                completado.setChecked(ped.isCompletado());
                completado.setEnabled(false);
                if(ped.isCompletado()){
                    btnSelect.setText("Completado");
                    btnSelect.setEnabled(false);
                }
                else{
                    btnSelect.setText("Completar");
                    btnSelect.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //Toast.makeText(NotificacionesActivity.this, "Agregar", Toast.LENGTH_SHORT).show();
                            Map<String, Object> PedidosMap= new HashMap<>();
                            PedidosMap.put("completado",true);
                            mDatabaseRefPed.child(ped.getKey()).updateChildren(PedidosMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(NotificacionesActivity.this, "Pedido Completado", Toast.LENGTH_SHORT).show();
                                    Refrescar();
                                }
                            });

                        }
                    });
                }
                row.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(NotificacionesActivity.this);
                        builder.setTitle("Pedido de la habitacion "+ped.getHabitacion());
                        builder.setMessage("Almohadas:"+ped.getAlmohadas()+
                                " Jabones:"+ped.getJabon()+
                                " Toallas:"+ped.getToallas()+
                                " Papel:"+ped.getPapel());
                        builder.setPositiveButton("Aceptar", null);

                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                });
                //row.addView(pos);
                row.addView(habitacion);
                /*row.addView(almohadas);
                row.addView(jabon);
                row.addView(papel);
                row.addView(toallas);*/
                row.addView(completado);
                row.addView(btnSelect);

                tPedidos.addView(row,i);
                i++;
            }
        }
        else{
            Toast.makeText(this, "No hay habitaciones disponibles para mostrar", Toast.LENGTH_SHORT).show();
        }
    }
    public interface OnGetDataListener {
        //make new interface for call back
        void onSuccess(DataSnapshot dataSnapshot);
        void onStart();
        void onFailure();
    }
    public void readData(DatabaseReference ref, final OnGetDataListener listener) {
        listener.onStart();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listener.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onFailure();
            }
        });

    }
    private void Refrescar(){
        Intent i= new Intent(this, NotificacionesActivity.class);
        startActivity(i);
    }
}