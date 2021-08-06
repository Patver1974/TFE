package com.example.tft_jeu.fragmentJeu;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tft_jeu.R;
import com.example.tft_jeu.models.StreetArt;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FramJeuSansMaps#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FramJeuSansMaps extends Fragment implements LocationListener {
    TextView tvCoordGps, tvCoordStreetArt,tvDistance;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
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
    // TODO: Rename and change types and number of parameters
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
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
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

        tvCoordGps = (TextView) v.findViewById(R.id.tv_jeusansmap_coordonneeactuelle);
        tvCoordStreetArt = (TextView) v.findViewById(R.id.tv_jeusansmap_coordonneestreetart);
        tvDistance = (TextView) v.findViewById(R.id.tv_jeusansmap_distance);
        Double latStreetARt = streetArt.getGeocoordinates().getLat();
        Double longStreetARt = streetArt.getGeocoordinates().getLon();

        String affichageCoordStreetARt = "Longitude" + streetArt.getGeocoordinates().getLon().toString() + " Latitude " + streetArt.getGeocoordinates().getLat().toString();


        return v;
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
float dist;
        String echelle;
      //  LocationManager lManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location l = new Location(LocationManager.GPS_PROVIDER);
        l.setLatitude(streetArt.getGeocoordinates().getLat());
        l.setLongitude(streetArt.getGeocoordinates().getLon());
        tvCoordStreetArt.setText(String.format("Streetart Latitude : %s Longitude : %s", streetArt.getGeocoordinates().getLat(), streetArt.getGeocoordinates().getLon()));
        tvCoordGps.setText(String.format("Ma position Latitude : %s Longitude : %s", location.getLatitude(), location.getLongitude()));
        streetArt.setDistance(location.distanceTo(l) / 1000);


        dist = streetArt.getDistance();
        if (dist<1) {echelle=" metres"; dist = Math.round(dist *1000);}
        else
        { echelle=" Km";dist = Math.round(dist*1000)/1000;};
        String nameArt = streetArt.getNameOfTheWork()==null?streetArt.getCategorie():streetArt.getNameOfTheWork().toString();
        String ligne = "L'oeuvre " +nameArt  + " est à " + dist + echelle +" de votre position";

        tvDistance.setText(ligne);

        //if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        //float dist = lManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).distanceTo(l);

    }
