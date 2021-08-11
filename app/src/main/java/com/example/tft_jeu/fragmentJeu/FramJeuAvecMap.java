package com.example.tft_jeu.fragmentJeu;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tft_jeu.R;
import com.example.tft_jeu.models.StreetArt;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FramJeuAvecMap#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FramJeuAvecMap extends Fragment implements OnMapReadyCallback, GoogleMap.InfoWindowAdapter {

    private final static int LOCATION_REQ_CODE = 456;
    private GoogleMap map;
    private LocationManager locationMgr = null;
    //  Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private StreetArt streetArt;

    public FramJeuAvecMap() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FramJeuAvecMap.
     */
    //  Rename and change types and number of parameters
    public static FramJeuAvecMap newInstance(String param1, String param2) {
        FramJeuAvecMap fragment = new FramJeuAvecMap();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            streetArt = getArguments().getParcelable("STREET_ART");
           // mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Demander la permission à l'utilisateur d'avoir votre localisation ...............................
        // Si je n'ai pas encore la permission :
        // getActivity() donne le contexte, l'activite qui est associée au fragment
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //je demande la permission
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQ_CODE);
        }        return inflater.inflate(R.layout.fragment_fram_jeu_avec_map, container, false);
    }

    @Nullable
    @Override
    public View getInfoWindow(@NonNull Marker marker) {
        return null;
    }
    @Nullable
    @Override
    public View getInfoContents(@NonNull Marker marker) {
        return null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d("MARIE", "Ok !!!");
        map = googleMap; // Le pointeur vers notre map
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        map.setMyLocationEnabled(true);
        // map.setInfoWindowAdapter(this);
        LatLng sydne = new LatLng(-34, 151);
        map.addMarker(new MarkerOptions().position(sydne).title("Marker in Sydney"));
        map.moveCamera(CameraUpdateFactory.newLatLng(sydne));
    }
}