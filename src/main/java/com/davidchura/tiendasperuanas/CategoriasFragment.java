package com.davidchura.tiendasperuanas;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoriasFragment extends Fragment {
    ListView mlvCategorias;

    public CategoriasFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_categorias, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mlvCategorias = view.findViewById(R.id.lvCategorias);
        leerDatos();
    }

    private void leerDatos() {
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = Total.ruta + "serviciocategorias.php";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("CATEGORIAS:",response);
                        mostrarLista(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                    Log.i("ERRORVOLLEY",error.getMessage());
            }
        });
        queue.add(stringRequest);
    }

    private void mostrarLista(String response) {
        try {
            JSONArray jsonArray = new JSONArray(response);
            //Se recuperan los datos en formato JSON
            String[] categorias = new String[jsonArray.length()];
            //jsonArray.length() cantidad de filas del JSON
            for(int i=0; i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                //JSONObject representa a acada fila del JSONarray
                String nombre = jsonObject.getString("nombre");
                //Asi se obtiene el contenido de un campo
                categorias[i] = nombre;
            }

            ListAdapter listAdapter = new ArrayAdapter<>(
                    getActivity(),android.R.layout.simple_list_item_1,categorias
            );
            mlvCategorias.setAdapter(listAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}










