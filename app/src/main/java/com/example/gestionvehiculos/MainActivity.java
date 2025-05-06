package com.example.gestionvehiculos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gestionvehiculos.Activities.GestorVehiculosActivity;
import com.example.gestionvehiculos.Activities.FormularioActivity;
import com.google.android.material.appbar.MaterialToolbar;

public class MainActivity extends AppCompatActivity {
    CardView gestorCard;
    CardView formularioCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // ESTA PARTE ES PARA EL TOOLBAR >:(
        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Gestor de Vehículos");
        for (int i = 0; i < toolbar.getChildCount(); i++) {
            if (toolbar.getChildAt(i) instanceof android.widget.TextView) {
                android.widget.TextView title = (android.widget.TextView) toolbar.getChildAt(i);
                title.setTextColor(getResources().getColor(android.R.color.white));
                break;
            }
        }

        // ESTA ES PARA LOS CARDVIEW
        gestorCard = findViewById(R.id.gestorCard);
        gestorCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GestorVehiculosActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

        formularioCard = findViewById(R.id.formularioCard);
        formularioCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FormularioActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}