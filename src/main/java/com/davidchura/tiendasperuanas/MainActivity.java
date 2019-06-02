package com.davidchura.tiendasperuanas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.davidchura.tiendasperuanas.iniciarsesion.InicioSesionActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button mbtnCrearCuenta;
    TextView mtvIniciarSesion;
    TextView mtvIP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mbtnCrearCuenta = findViewById(R.id.btnCrearCuenta);
        mtvIniciarSesion= findViewById(R.id.tvIniciarSesion);
        mtvIP = findViewById(R.id.tvIP);


        mbtnCrearCuenta.setOnClickListener(this);
        mtvIniciarSesion.setOnClickListener(this);

        leerIP();
    }

    private void leerIP() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://api.ipify.org/";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        mtvIP.setText(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mtvIP.setText("No funciona!");
                Log.i("ERRORVOLLEY",error.getMessage());
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        //v.getId() devuelve el id del objeto que gene{o el evento
        switch (v.getId()){
            case R.id.btnCrearCuenta:
                mostrarCrearCuenta();
                break;
            case R.id.tvIniciarSesion:
                mostrarIniciarSesion();
                break;
        }
    }

    private void mostrarIniciarSesion() {
        startActivity(new Intent(this, InicioSesionActivity.class));
    }

    private void mostrarCrearCuenta() {
        startActivity(new Intent(this,CrearCuentaActivity.class));
    }
}











