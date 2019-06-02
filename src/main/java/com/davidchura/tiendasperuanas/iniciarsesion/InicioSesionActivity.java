package com.davidchura.tiendasperuanas.iniciarsesion;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.davidchura.tiendasperuanas.EscritorioActivity;
import com.davidchura.tiendasperuanas.R;
import com.davidchura.tiendasperuanas.Total;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class InicioSesionActivity extends AppCompatActivity {
    ImageView mivFotografia;
    EditText metUsuario, metClave;
    CheckBox mchkSesion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);
        mivFotografia = findViewById(R.id.ivFotografia);
        metUsuario = findViewById(R.id.etUsuario);
        metClave = findViewById(R.id.etClave);
        mchkSesion = findViewById(R.id.checkSesion);


        String ruta = "https://www.scified.com/articles/fan-art-spotlight-godzilla-2-king-the-monsters-april-2019-6.jpg";
        Picasso.get().load(ruta).into(mivFotografia);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_iniciar_sesion, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.nav_informacion:
                mostrarInformacion(); 
                return true;
            case R.id.nav_acerca_de:
                mostrarAcercade(); 
                return true;
            case R.id.nav_ayuda:
                mostrarAyuda();
                return true;    
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void mostrarInformacion() {
        startActivity(new Intent(this,InformacionActivity.class));
    }

    private void mostrarAcercade() {
        startActivity(new Intent(this,AcercadeActivity.class));
    }

    private void mostrarAyuda() {
        startActivity(new Intent(this,AyudaActivity.class));
    }

    public void iniciarSesion(View view) {
        final String usuario = metUsuario.getText().toString();
        final String clave = metClave.getText().toString();

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = Total.ruta + "iniciarsesion.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Repuesta :",response);
                        evaluarInicioSesion(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("ERRORVOLLEY",error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("usuario",usuario);
                map.put("clave",clave);
                return map;
            }
        };
        queue.add(stringRequest);
    }
    private void evaluarInicioSesion(String response) {
        if(response.equals("-1")){
            Toast.makeText(this, "El usuario no existe", Toast.LENGTH_SHORT).show();
        }else if(response.equals("-2")){
            Toast.makeText(this, "La constraseña es incorrecta", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this,EscritorioActivity.class));
            vverificarSesion(response);
        }
    }
    private void vverificarSesion(String response){
        if(mchkSesion.isSelected()==true){
            SharedPreferences sharedPreferences = getSharedPreferences(
                    "SesionApp", Context.MODE_PRIVATE
            );
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("datosUsuario",response);
            editor.commit();
        }
    }
}
