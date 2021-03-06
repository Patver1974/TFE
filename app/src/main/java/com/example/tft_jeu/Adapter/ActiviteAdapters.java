package com.example.tft_jeu.Adapter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.CancellationSignal;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tft_jeu.R;
import com.example.tft_jeu.models.Geocoordinates;
import com.example.tft_jeu.models.StreetArt;

import java.util.List;


public class ActiviteAdapters extends RecyclerView.Adapter<ActiviteAdapters.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvNameOeuvre, tvNameArtist, tvLat, tvLong, tvAdresse, tvDistance;
        //private CardView cvCategory;

        public ViewHolder(@NonNull View view) {
            super(view);

            tvNameOeuvre = view.findViewById(R.id.item_art_name_oeuvre);
            tvNameArtist = view.findViewById(R.id.item_art_name_artist);
            tvLat = view.findViewById(R.id.item_activite_coordonnee_lat);
            tvLong = view.findViewById(R.id.item_activite_coordonnee_long);
            tvAdresse = view.findViewById(R.id.item_activite_adresse);
            tvDistance= view.findViewById(R.id.item_activite_distance);
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
        public TextView getTvDistance() {
            return tvDistance;
        }
    }


    private List<StreetArt> dataSet; // Utilisation du type interface (D??couplage)
    private Context context;
    private LocationManager lManager;
private String echelle,nameArt;
    public ActiviteAdapters(Context context, List<StreetArt> dataSet, LocationManager locationManager) {
        this.context = context;
        this.dataSet = dataSet;
        this.lManager = locationManager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_streetart, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onBindViewHolder(@NonNull ActiviteAdapters.ViewHolder holder, int position) {
        String strDist;
        StreetArt streetArt = dataSet.get(position);
        holder.getTvNameOeuvre().setText(streetArt.getNameOfTheWork().toString());
        holder.getTvNameArtist().setText(streetArt.getNameOfTheArtist());

        String streetArtlatitudestring = String.valueOf((Math.round(streetArt.getGeocoordinates().getLat()*10000))/10000.0);
        String streetArtLongitudestring = String.valueOf((Math.round(streetArt.getGeocoordinates().getLon()*10000))/10000.0);
        holder.getTvLatitude().setText(streetArtlatitudestring);
        holder.getTvLongitude().setText(streetArtLongitudestring);

        holder.getTvAdresse().setText(streetArt.getAdresse());
        Log.d("adress activadpt", "adresses activiteadapter "+streetArt.getAdresse());


        Location l = new Location(LocationManager.GPS_PROVIDER);
        l.setLatitude(streetArt.getGeocoordinates().getLat());
        l.setLongitude(streetArt.getGeocoordinates().getLon());

        float dist = lManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).distanceTo(l)/1000;
        Log.d("DISTANCE", streetArt.getNameOfTheWork()+ ": distance= "+ dist);
        //arrondir
        if (dist<1) {

        strDist = String.valueOf(Math.round(dist *1000))+ " metres";
    }
        else {
            strDist = ((Math.round(dist * 10000)) / 10000.0) + " km";
        }


        nameArt = streetArt.getNameOfTheWork()==null?streetArt.getCategorie():streetArt.getNameOfTheWork().toString();
        String ligne = "L'oeuvre " +nameArt   + " est ?? " + strDist +" de votre position";


        holder.getTvDistance().setText(String.valueOf(ligne));

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}

