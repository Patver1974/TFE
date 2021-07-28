package com.example.tft_jeu.Adapter;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
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
    private LocationManager locationManager;
    private final View.OnClickListener onClickListener;
    public StreetArtAdapater(List<StreetArt> streetArts, View.OnClickListener onClickListener) {
        this.streetArts = streetArts;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public StreetArtViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_streetart, parent, false);

        view.setOnClickListener(this.onClickListener);

        return new StreetArtViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StreetArtAdapater.StreetArtViewHolder holder, int position) {
        StreetArt streetArt = streetArts.get(position);

        holder.bindData(streetArt);
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

        public StreetArtViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNameArtist = itemView.findViewById(R.id.item_art_name_artist);
            tvNameOeuvre = itemView.findViewById(R.id.item_art_name_oeuvre);
            tvLat = itemView.findViewById(R.id.item_activite_coordonnee_lat);
            tvLong = itemView.findViewById(R.id.item_activite_coordonnee_long);
            tvAdresse = itemView.findViewById(R.id.item_activite_adresse);

        }

        public void bindData(StreetArt streetArt) {
            this.tvNameArtist.setText(streetArt.getNomDeLArtiste());
            this.tvNameOeuvre.setText(streetArt.getNameOfTheWork() != null ? streetArt.getNameOfTheWork().toString() : "Pas de nom");
            String latitude = streetArt.getGeocoordinates().getLat().toString();
            String longitude = streetArt.getGeocoordinates().getLon().toString();
            this.tvLat.setText(latitude);
            this.tvLong.setText(longitude);
            this.tvAdresse.setText(streetArt.getAdresse());
            Calculkm(latitude,longitude);

            }

        private void Calculkm(String latitude, String longitude) {

          //  Location.distanceBetween(sydney.latitude, sydney.longitude, location.getLatitude(), location.getLongitude(), result);

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
    }
}
