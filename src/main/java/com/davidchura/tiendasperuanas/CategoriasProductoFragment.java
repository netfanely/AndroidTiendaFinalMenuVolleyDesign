package com.davidchura.tiendasperuanas;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

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


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoriasProductoFragment extends Fragment implements AdapterView.OnItemClickListener {
    ListView mlvCategorias;
    ArrayList<HashMap<String,String>> arrayList;

    public CategoriasProductoFragment() {
        // Required empty public constructor
        arrayList = new ArrayList<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_categorias_producto, container, false);
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
            for(int i = 0 ; i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String idcategoria = jsonObject.getString("idcategoria");
                String nombre = jsonObject.getString("nombre");
                String descripcion = jsonObject.getString("descripcion");

                HashMap<String,String> map = new HashMap<>();
                map.put("cod",idcategoria);
                map.put("nom",nombre);
                map.put("des",descripcion);

                arrayList.add(map);//Se añade el hashmap al araylist
            }

            ListAdapter listAdapter = new SimpleAdapter(
                    getActivity(), arrayList, R.layout.item_categorias,
                    new String[]{"cod","nom","des"},
                    new int[]{R.id.tvCodigo,R.id.tvNombre,R.id.tvDescripcion}
            );
            mlvCategorias.setAdapter(listAdapter);

            mlvCategorias.setOnItemClickListener(this);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        HashMap<String,String> map = arrayList.get(position);
        //se recupera el elemento seleccionado
        String idcategoria = map.get("cod");
        String nombre = map.get("nom");
        String descripcion = map.get("des");
        Toast.makeText(getActivity(),nombre,Toast.LENGTH_SHORT).show();

        ProductosFragment productosFragment = new ProductosFragment();
        Bundle bundle = new Bundle();
        bundle.putString("idcategoria",idcategoria);
        bundle.putString("nombre",nombre);
        bundle.putString("descripcion",descripcion);

        productosFragment.setArguments(bundle);

        getFragmentManager().beginTransaction()
                .replace(R.id.contenedor_principal, productosFragment)
                .commit();
    }
}
