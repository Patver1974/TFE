package com.example.tft_jeu.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tft_jeu.R;
import com.example.tft_jeu.models.StreetArt;

import java.util.List;


public class ActiviteAdapters extends RecyclerView.Adapter<ActiviteAdapters.ViewHolder> {


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvNameOeuvre, tvNameArtist, tvLat,tvLong, tvAdresse;
        //private CardView cvCategory;

        public ViewHolder(@NonNull View view) {
            super(view);

            tvNameOeuvre = view.findViewById(R.id.item_art_name_oeuvre);
            tvNameArtist= view.findViewById(R.id.item_art_name_artist);
            tvLat = view.findViewById(R.id.item_activite_coordonnee_lat);
            tvLong = view.findViewById(R.id.item_activite_coordonnee_long);
            tvAdresse = view.findViewById(R.id.item_activite_adresse);

        }

        public TextView getTvNameOeuvre() {
            return tvNameOeuvre;
        }
        public TextView getTvNameArtist() {
            return tvNameArtist;
        }
        public TextView getTvLatitude() {
            return tvLat;
        }
        public TextView getTvLongitude() {
            return tvLong;
        }
        public TextView getTvAdresse() {
            return tvAdresse;
        }
    }


    private List<StreetArt> dataSet; // Utilisation du type interface (Découplage)
    private Context context;

    public ActiviteAdapters(Context context, List<StreetArt> dataSet) {
        this.context = context;
        this.dataSet = dataSet;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_streetart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActiviteAdapters.ViewHolder holder, int position) {
        StreetArt streetArt = dataSet.get(position);
        holder.getTvNameOeuvre().setText(streetArt.getNameOfTheWork().toString());
        holder.getTvNameArtist().setText(streetArt.getNomDeLArtiste());
        holder.getTvLatitude().setText(streetArt.getGeocoordinates().getLat().toString());
        holder.getTvLongitude().setText(streetArt.getGeocoordinates().getLon().toString());
        holder.getTvAdresse().setText(streetArt.getAdresse());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}

