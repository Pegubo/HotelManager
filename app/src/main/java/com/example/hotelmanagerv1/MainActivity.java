package com.example.hotelmanagerv1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public CardView lavadora,limpieza,productos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lavadora=(CardView)findViewById(R.id.cv_Lavanderia);
        limpieza=(CardView)findViewById(R.id.cv_Limpieza);
        productos=(CardView)findViewById(R.id.cv_Productos);

        lavadora.setOnClickListener(this);
        limpieza.setOnClickListener(this);
        productos.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;

        switch (v.getId()){

            case R.id.cv_Lavanderia:
            i=new Intent(this,lavanderia.class);
            startActivity(i);
            break;

            case R.id.cv_Limpieza:
                i=new Intent(this,limpieza.class);
                startActivity(i);
                break;

            case R.id.cv_Productos:
                i=new Intent(this,productos.class);
                startActivity(i);
                break;
        }


    }

    public void logout(View v){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        finish();
    }
}