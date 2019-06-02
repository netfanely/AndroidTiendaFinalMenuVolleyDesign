package com.davidchura.tiendasperuanas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //Para que no se vea la barra de estado o de notificaciones
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Se ejecutar{a el método run de Runnable después de el
        //tiempo indicado en milisegundos
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mostrarInicio();
            }
        }, 3000);

    }

    private void mostrarInicio() {
        SharedPreferences sharedPreferences = getSharedPreferences(
                "SesionApp",MODE_PRIVATE
        );
        String datosUsuarios = sharedPreferences.getString("datosUsuarios","avc");
        if(datosUsuarios.equals("abc")){
            startActivity(new Intent(this, MainActivity.class));
        }else{
            startActivity(new Intent(this,EscritorioActivity.class));
        }
        finish();//Para cerrar el activity
    }
}
