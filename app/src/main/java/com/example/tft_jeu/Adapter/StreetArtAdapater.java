package com.example.tft_jeu.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tft_jeu.R;
import com.example.tft_jeu.models.StreetArt;

import java.util.List;

public class StreetArtAdapater extends RecyclerView.Adapter<StreetArtAdapater.StreetArtViewHolder> {

    private final List<StreetArt> streetArts;
    private final View.OnClickListener onClickListener;
    private LocationManager lManager;

    public StreetArtAdapater(List<StreetArt> streetArts, View.OnClickListener onClickListener, LocationManager locationManager) {
        this.streetArts = streetArts;
        this.onClickListener = onClickListener;
        this.lManager = locationManager;
    }

    @NonNull
    @Override
    public StreetArtViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_streetart, parent, false);

        view.setOnClickListener(this.onClickListener);

        return new StreetArtViewHolder(view);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onBindViewHolder(@NonNull StreetArtAdapater.StreetArtViewHolder holder, int position) {
        StreetArt streetArt = streetArts.get(position);
        String echelle, nameArt;
        holder.bindData(streetArt);

        // holder.getTvNameOeuvre().setText(streetArt.getNameOfTheWork().toString());
        //holder.getTvNameArtist().setText(streetArt.getNomDeLArtiste());
        //holder.getTvLat().setText(streetArt.getGeocoordinates().getLat().toString());
        //holder.getTvLong().setText(streetArt.getGeocoordinates().getLon().toString());
        // holder.getTvAdresse().setText(streetArt.getAdresse());

        Location l = new Location(LocationManager.GPS_PROVIDER);

        l.setLatitude(streetArt.getGeocoordinates().getLat());
        l.setLongitude(streetArt.getGeocoordinates().getLon());

        Location location = lManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        float dist = location.distanceTo(l) / 1000;
        String strDist;

        Log.d("DISTANCE", streetArt.getNameOfTheWork() + ": distance= " + dist);
        //ternaire
        //echelle = dist>1 ? "Km":"metres";

        //arrondir
        if (dist < 1) {
            strDist = String.valueOf(Math.round(dist * 1000)) + " metres";
        } else {
            strDist = ((Math.round(dist * 10000)) / 10000.0) + " km";


        }
        ;
        nameArt = streetArt.getNameOfTheWork() == null ? streetArt.getCategorie() : streetArt.getNameOfTheWork().toString();
        String ligne = "L'oeuvre " + nameArt + " est à " + strDist + " de votre position";
        holder.getTvDistance().setText(ligne);


    }

    @Override
    public int getItemCount() {
        return streetArts.size();
    }

    public static class StreetArtViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNameOeuvre;
        private TextView tvNameArtist;
        private TextView tvLat;
        private TextView tvLong;
        private TextView tvAdresse;
        private TextView tvDistance;

        public StreetArtViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNameArtist = itemView.findViewById(R.id.item_art_name_artist);
            tvNameOeuvre = itemView.findViewById(R.id.item_art_name_oeuvre);
            tvLat = itemView.findViewById(R.id.item_activite_coordonnee_lat);
            tvLong = itemView.findViewById(R.id.item_activite_coordonnee_long);
            tvAdresse = itemView.findViewById(R.id.item_activite_adresse);
            tvDistance = itemView.findViewById(R.id.item_activite_distance);
        }

        public void bindData(StreetArt streetArt) {
            this.tvNameArtist.setText(streetArt.getNameOfTheArtist());
            this.tvNameOeuvre.setText(streetArt.getNameOfTheWork() != null ? streetArt.getNameOfTheWork().toString() : "Pas de nom");

            String streetArtlatitudestring = String.valueOf((Math.round(streetArt.getGeocoordinates().getLat() * 10000)) / 10000.0);
            String streetArtLongitudestring = String.valueOf((Math.round(streetArt.getGeocoordinates().getLon() * 10000)) / 10000.0);


            this.tvLat.setText(streetArtlatitudestring);
            this.tvLong.setText(streetArtLongitudestring);
            this.tvAdresse.setText(streetArt.getAdresse());
            Log.d("adresstreetartada", "adresses streetartadapter"+streetArt.getAdresse());
        }

        public TextView getTvNameOeuvre() {
            return tvNameOeuvre;
        }

        public void setTvNameOeuvre(TextView tvNameOeuvre) {
            this.tvNameOeuvre = tvNameOeuvre;
        }

        public TextView getTvNameArtist() {
            return tvNameArtist;
        }

        public void setTvNameArtist(TextView tvNameArtist) {
            this.tvNameArtist = tvNameArtist;
        }

        public TextView getTvLat() {
            return tvLat;
        }

        public void setTvLat(TextView tvLat) {
            this.tvLat = tvLat;
        }

        public TextView getTvLong() {
            return tvLong;
        }

        public void setTvLong(TextView tvLong) {
            this.tvLong = tvLong;
        }

        public TextView getTvAdresse() {
            return tvAdresse;
        }

        public void setTvAdresse(TextView tvAdresse) {
            this.tvAdresse = tvAdresse;
        }

        public TextView getTvDistance() {
            return tvDistance;
        }

        public void setTvDistance(TextView tvDistance) {
            this.tvDistance = tvDistance;
        }
    }
}
