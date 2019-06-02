package com.davidchura.tiendasperuanas.iniciarsesion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.davidchura.tiendasperuanas.R;

public class AyudaActivity extends AppCompatActivity {
    ListView mlvConsejos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayuda);
        mlvConsejos = findViewById(R.id.lvConsejos);

        String[] Consejos = new String[]{
            "Use contraseñas complejas",
            "Cambie su contraseña periódicamente",
            "No pierda de vista sus dispositivos personales",
            "No acepte enlaces desconocidos"
        };

        ListAdapter listAdapter = new ArrayAdapter<>(
                this,android.R.layout.simple_list_item_1,Consejos
        );
        mlvConsejos.setAdapter(listAdapter);
    }
}
