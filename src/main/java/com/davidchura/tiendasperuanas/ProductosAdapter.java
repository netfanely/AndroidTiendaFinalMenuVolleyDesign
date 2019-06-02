package com.davidchura.tiendasperuanas;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductosAdapter extends ArrayAdapter {
    ArrayList arrayList;
    Activity activity;

    public ProductosAdapter(Activity activity, ArrayList arrayList) {
        super(activity, R.layout.item_productos,arrayList);
        this.arrayList = arrayList;
        this.activity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rootView = activity.getLayoutInflater().inflate(R.layout.item_productos,null);
        TextView mtvCodigo = rootView.findViewById(R.id.tvCodigo);
        TextView mtvNombre = rootView.findViewById(R.id.tvNombre);
        TextView mtvDetalle = rootView.findViewById(R.id.tvDetalle);
        ImageView mivFoto = rootView.findViewById(R.id.ivFoto);

        HashMap<String,String> map = (HashMap<String,String>)arrayList.get(position);

        String idproducto = map.get("cod");
        String nombre = map.get("nom");
        String detalle = map.get("det");
        String imagenchica = map.get("fot");

        mtvCodigo.setText(idproducto);
        mtvNombre.setText(nombre);
        mtvDetalle.setText(detalle);

        String rutaFoto = Total.ruta + imagenchica;

        Picasso.get().load(rutaFoto).into(mivFoto);

        return rootView;
    }
}
