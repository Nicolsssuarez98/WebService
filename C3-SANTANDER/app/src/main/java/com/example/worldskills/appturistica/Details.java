package com.example.worldskills.appturistica;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Details extends AppCompatActivity {
    static String  Nombre,  descripcion, latitud, longitud, urlimagen;
    ImageView imageR;
    FloatingActionButton fab;
    TextView txtN, txtD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        imageR = findViewById(R.id.imR);
        txtN = findViewById(R.id.txtNr);
        txtD = findViewById(R.id.txtDc);
        Picasso.get().load(urlimagen).into(imageR);
        txtN.setText(Nombre);
        txtD.setText(descripcion);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Details.this, MapsActivity2.class));

            }
        });
    }
}
