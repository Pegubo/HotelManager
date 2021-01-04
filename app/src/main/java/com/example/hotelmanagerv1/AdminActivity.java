package com.example.hotelmanagerv1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AdminActivity extends AppCompatActivity {
    CardView cHabitacion;
    CardView cNotificaciones;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        cHabitacion= findViewById(R.id.cv_habitacion);
        cNotificaciones= findViewById(R.id.cv_notificaciones);

        cHabitacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHabitacionActivity();
            }
        });
    }
    private void openHabitacionActivity() {
        Intent intent = new Intent(this, HabitacionActivity.class);
        startActivity(intent);
    }
}