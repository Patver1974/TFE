package com.example.tft_jeu.fragmentJeu;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tft_jeu.R;
import com.example.tft_jeu.models.StreetArt;

import java.text.DecimalFormat;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FramJeuSansMaps#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FramJeuSansMaps extends Fragment implements LocationListener {
    TextView tvCoordGps, tvCoordStreetArt, tvDistance, tvEstOuest, tvNordSud, tvrapprochement;
    ImageView imgArrivee;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private StreetArt streetArt;
    private LocationManager lManager;


    public FramJeuSansMaps() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FramJeuSansMaps.
     */

    public static FramJeuSansMaps newInstance(String param1, String param2) {
        FramJeuSansMaps fragment = new FramJeuSansMaps();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);


        if (getArguments() != null) {
            streetArt = getArguments().getParcelable("STREET_ART");
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        if (lManager == null) {
            return;
        }
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        lManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View v = inflater.inflate(R.layout.fragment_fram_jeu_sans_maps, container, false);


        tvrapprochement = (TextView) v.findViewById(R.id.tv_jeusansmap_rapprochement);
        tvCoordGps = (TextView) v.findViewById(R.id.tv_jeusansmap_coordonneeactuelle);
        tvCoordStreetArt = (TextView) v.findViewById(R.id.tv_jeusansmap_coordonneestreetart);
        tvDistance = (TextView) v.findViewById(R.id.tv_jeusansmap_distance);
        tvEstOuest = (TextView) v.findViewById(R.id.tv_jeusansmap_estouest);
        tvNordSud = (TextView) v.findViewById(R.id.tv_jeusansmap_nordsud);
        imgArrivee = (ImageView) v.findViewById(R.id.img_jeusansmap_img);


        Double latStreetARt = streetArt.getGeocoordinates().getLat();
        Double longStreetARt = streetArt.getGeocoordinates().getLon();

        String affichageCoordStreetARt = "Longitude" + streetArt.getGeocoordinates().getLon().toString() + " Latitude " + streetArt.getGeocoordinates().getLat().toString();


        return v;
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        // TODO: persistance ancienne distance
       // SharedPreferences rememberAncienneDistan = PreferenceManager.getDefaultSharedPreferences(getContext());
        //SharedPreferences.Editor editor = rememberAncienneDistan.edit();

        float dist;
        String echelle = " ";
        //  LocationManager lManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location l = new Location(LocationManager.GPS_PROVIDER);
        l.setLatitude(streetArt.getGeocoordinates().getLat());
        l.setLongitude(streetArt.getGeocoordinates().getLon());
        String gpsLatitudeString=String.valueOf(Math.round(location.getLatitude()*10000)/10000.0);
                String gpsLongitudeString=String.valueOf(Math.round(location.getLongitude()*10000)/10000.0);


String streetArtlatitudestring = String.valueOf((Math.round(streetArt.getGeocoordinates().getLat()*10000))/10000.0);
        String streetArtLongitudestring = String.valueOf((Math.round(streetArt.getGeocoordinates().getLon()*10000))/10000.0);

        tvCoordStreetArt.setText(String.format("Streetart \n Latitude : %s Longitude : %s", streetArtlatitudestring, streetArtLongitudestring));
        tvCoordGps.setText(String.format("Latitude : %s Longitude : %s", gpsLatitudeString, gpsLongitudeString));
        streetArt.setDistance(location.distanceTo(l) / 1000);

        String strDist = "";
        dist = streetArt.getDistance();
        if (dist < 1) {
            echelle = " metres";
            dist = Math.round(dist * 1000);
            strDist = (int) dist + echelle;
            if (dist > 300) {
                imgArrivee.setImageResource(R.drawable.boussole2);
            } else {
                imgArrivee.setImageResource(R.drawable.arriveecourse);
            }
        } else {

            echelle = " Km";
            strDist =  ((Math.round(dist*10000)) /10000.0) + echelle;
        }
        ;
        String nameArt = streetArt.getNameOfTheWork() == null ? streetArt.getCategorie() : streetArt.getNameOfTheWork().toString();
        String ligne = "L'oeuvre " + nameArt + " est à " + strDist + " de votre position";
        tvDistance.setText(ligne);


        // Insérer ma String (StringParDefaut, nomInput)
// TODO: persistance ancienne distance
       // editor.putString("ancienneDistance", String.valueOf(dist));

        // sauvegarder
        //editor.apply();


        boolean isNorth = location.getLatitude() > l.getLatitude();
        Location northLocation = new Location(LocationManager.GPS_PROVIDER);
        northLocation.setLatitude(location.getLatitude());
        northLocation.setLongitude(l.getLongitude());
        double northDistance = location.distanceTo(northLocation) / 1000;
        String northDistanceStr = "";
        if (northDistance < 1) {
            echelle = " metres";
            northDistanceStr = String.valueOf(Math.round(northDistance * 1000))+ echelle;
        } else {
            echelle = " Km";
            Log.d("Echelle", echelle);
            northDistanceStr = (Math.round(northDistance * 10000)) / 10000.0 + echelle;
        }

        boolean isEst = location.getLongitude() > l.getLongitude();
        Location estLocation = new Location(LocationManager.GPS_PROVIDER);
        estLocation.setLongitude(location.getLongitude());
        estLocation.setLatitude(l.getLatitude());
        double estDistance = location.distanceTo(estLocation) / 1000.0;
        String estDistanceStr ="";

        if (estDistance < 1) {
            echelle = " metres";
            estDistanceStr = Math.round(estDistance * 1000) + echelle;
        } else {
            echelle = " Km";
            estDistanceStr = (Math.round(estDistance * 10000)) / 10000.0 + echelle;
        }


        String affichageNordSud = !isNorth ? String.format("L'oeuvre est au Sud %s ", northDistanceStr) : String.format("L'oeuvre est au Nord %s ", northDistanceStr);
        String affichageEstOuest = estDistance > 0 ? String.format("L'oeuvre est au Ouest %s ", estDistanceStr) : String.format("L'oeuvre est au Est %s %s", estDistanceStr);
        tvNordSud.setText(affichageNordSud);
        tvEstOuest.setText(affichageEstOuest);

        //if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        // here to request the missing permissions, and then overriding
        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
        //                                          int[] grantResults)
        // to handle the case where the user grants the permission. See the documentation
        // for ActivityCompat#requestPermissions for more details.
        return;
    }


}
