package com.example.worldskills.appturistica;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.worldskills.appturistica.modelo.Mymodel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RvPlaces extends RecyclerView.Adapter<RvPlaces.ViewPlaces> {
    List<Mymodel> mymodels;

    public RvPlaces(List<Mymodel> filter) {
        mymodels = filter;
    }

    @NonNull
    @Override
    public RvPlaces.ViewPlaces onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        int camV= ListBy.towCards ? R.layout.list1 : R.layout.list;

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(camV, viewGroup,false);
        ViewPlaces vP = new ViewPlaces(v);
        return vP;
    }

    @Override
    public void onBindViewHolder(@NonNull RvPlaces.ViewPlaces viewPlaces,final int i) {
        Picasso.get().load(mymodels.get(i).getUrlimagen()).into(viewPlaces.im);
        viewPlaces.nom.setText(mymodels.get(i).getNombre());
        viewPlaces.desc.setText(mymodels.get(i).getDescripcioncorta());
        viewPlaces.ubi.setText(mymodels.get(i).getUbicacion());
        viewPlaces.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ir = new Intent(v.getContext(), Details.class);
                Details.Nombre = mymodels.get(i).getNombre();
                Details.latitud = mymodels.get(i).getLatitud();
                Details.longitud = mymodels.get(i).getLongitud();
                Details.descripcion = mymodels.get(i).getDescripcion();
                Details.urlimagen = mymodels.get(i).getUrlimagen();
                v.getContext().startActivity(ir);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mymodels.size();
    }

    public class ViewPlaces extends RecyclerView.ViewHolder {
        ImageView im;
        TextView nom, desc, ubi;
        public ViewPlaces(@NonNull View itemView) {
            super(itemView);
            im = itemView.findViewById(R.id.imagesV);
            nom = itemView.findViewById(R.id.nombreS);
            desc = itemView.findViewById(R.id.desc);
            ubi = itemView.findViewById(R.id.ubi);
        }
    }
}
