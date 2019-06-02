package com.davidchura.tiendasperuanas;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductosFragment extends Fragment {
    TextView mtvNombre;
    String idcategoria;
    ArrayList<HashMap<String,String>> arrayList;
    ListView lvProductos;
    public ProductosFragment() {
        // Required empty public constructor
        arrayList = new ArrayList<>();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_productos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mtvNombre = view.findViewById(R.id.tvNombre);
        lvProductos = view.findViewById(R.id.lvProductos);
        Bundle bundle = getArguments();
        idcategoria = bundle.getString("idcategoria");
        String nombre = bundle.getString("nombre");
        String descripcion = bundle.getString("descripcion");
        mtvNombre.setText(nombre);
        getActivity().setTitle(descripcion);
        leerDatos();
    }
    private void leerDatos() {
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = Total.ruta + "servicioproductos.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("PRODUCTOS:",response);
                        mostrarLista(response);
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
                map.put("caty",idcategoria);
                return map;
            }
        };
        queue.add(stringRequest);
    }
    private void mostrarLista(String response) {
        try {
            JSONArray jsonArray = new JSONArray(response);
            for(int i = 0 ; i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String idproducto = jsonObject.getString("idproducto");
                String nombre = jsonObject.getString("nombre");
                String detalle = jsonObject.getString("detalle");
                String imagenchica = jsonObject.getString("imagenchica");

                HashMap<String,String> map = new HashMap<>();
                map.put("cod",idproducto);
                map.put("nom",nombre);
                map.put("det",detalle);
                map.put("fot",imagenchica);

                arrayList.add(map);//Se añade el hashmap al araylist
            }
/*
            ListAdapter listAdapter = new SimpleAdapter(
                    getActivity(), arrayList, R.layout.item_productos,
                    new String[]{"cod","nom","det"},
                    new int[]{R.id.tvCodigo,R.id.tvNombre,R.id.tvDetalle}
            );
            lvProductos.setAdapter(listAdapter);
*/
            ProductosAdapter productosAdapter = new ProductosAdapter(getActivity(),arrayList);
            lvProductos.setAdapter(productosAdapter);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
