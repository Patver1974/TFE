package com.example.tft_jeu.menugestionbsd;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tft_jeu.helpergps.AppConstants;
import com.example.tft_jeu.helpergps.GpsUtils;
import com.example.tft_jeu.R;
import com.example.tft_jeu.db.dao.StreetDao;
import com.example.tft_jeu.models.Geocoordinates;
import com.example.tft_jeu.models.Photo;
import com.example.tft_jeu.models.StreetArt;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FrMenuGestionBsdUpdate#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FrMenuGestionBsdUpdate extends Fragment implements View.OnClickListener, OnMapReadyCallback {
    private LocationManager locationManager;
    private Spinner spListeDatabase;
    private Button btprecedent, btsuivant, btlieuxgps, btmodifier, btdelete;
    private TextView tvidbsd;
    private EditText etnomlieux, etadresse, etlongitude, etlatitude, etCategories;
    private List<StreetArt> listeStreetArt = new ArrayList<>();
    private Integer postionliste = 0;
    private Integer maxIdListeSteetArt;

    private String latitudeString;
    private String longitudeString;
    private GoogleMap map;
    private StreetDao dao;
    private List<String> choiceCategories;
    private final ArrayList<LatLng> lstLatLng = new ArrayList<LatLng>();

    private FusedLocationProviderClient mFusedLocationClient;
    private boolean isContinue = false;
    private boolean isGPS = false;
    private double wayLatitude = 0.0, wayLongitude = 0.0;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public FrMenuGestionBsdUpdate() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FrMenuGestionBsdUpdate.
     */
    // TODO: Rename and change types and number of parameters
    public static FrMenuGestionBsdUpdate newInstance() {
        FrMenuGestionBsdUpdate fragment = new FrMenuGestionBsdUpdate();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onStart() {
        super.onStart();

        // Récupère le fragment représentant la map. Fragment dans un fragment
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map_upbdate);
        // récupère la carte et fait une synchronisation
        mapFragment.getMapAsync(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fr_menu_gestion_bsd_update, container, false);

        dao = new StreetDao(getContext());
        dao.openReadable();
        choiceCategories = dao.getAllCategories();
        dao.close();

        String categorie = "";


        spListeDatabase = v.findViewById(R.id.sp_menuBsdUpdate_listecategories);
        btprecedent = v.findViewById(R.id.bt_menuBsdUpdate_precedentitem);
        btsuivant = v.findViewById(R.id.bt_menuBsdUpdate_suivantitem);
        tvidbsd = v.findViewById(R.id.tv_menuBsdUpdate_idbsd);
        etnomlieux = v.findViewById(R.id.et_menuBsdUpdate_nomlieux);
        etadresse = v.findViewById(R.id.et_menuBsdUpdate_adresselieux);
        etlongitude = v.findViewById(R.id.et_menuBsdUpdate_coordLongitude);
        etlatitude = v.findViewById(R.id.et_menuBsdUpdate_coordLatitude);
        btlieuxgps = v.findViewById(R.id.bt_menuBsdUpdate_affichageLatLongGps);
        btmodifier = v.findViewById(R.id.bt_menuBsdUpdate_modifierbsd);
        etCategories = v.findViewById(R.id.et_menuBsdUpdate_categorie);
        btdelete = v.findViewById(R.id.bt_menuBsdUpdate_del_bsd);

        btmodifier.setOnClickListener(this);
        btprecedent.setOnClickListener(this);
        btsuivant.setOnClickListener(this);
        btlieuxgps.setOnClickListener(this);
        btdelete.setOnClickListener(this);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        updatespinner();
        MiseAJourListeStreetArt();


        new GpsUtils(getContext()).turnGPSOn(new GpsUtils.onGpsListener() {
            @Override
            public void gpsStatus(boolean isGPSEnable) {
                // turn on GPS
                isGPS = isGPSEnable;
            }
        });

        spListeDatabase
                .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1,
                                               int pos, long id) {


                        if (!spListeDatabase.getSelectedItem().toString().equals(getResources().getString(R.string.ToutesLesCategories))) {
                            etCategories.setText(spListeDatabase.getSelectedItem().toString());
                        }


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub

                    }
                });


        return v;
    }

    private void MiseAJourListeStreetArt() {
        listeStreetArt.clear();
        dao = new StreetDao(getContext());
        dao.openReadable();
        listeStreetArt = dao.getAll();
        dao.close();
        maxIdListeSteetArt = listeStreetArt.size();
        etCategories.setText(String.valueOf(listeStreetArt.get(postionliste).getCategorie()));
        etnomlieux.setText(String.valueOf(listeStreetArt.get(postionliste).getNameOfTheWork()));
        etadresse.setText(String.valueOf(listeStreetArt.get(postionliste).getAdresse()));
        etlongitude.setText(String.valueOf(listeStreetArt.get(postionliste).getGeocoordinates().getLon()));
        etlatitude.setText(String.valueOf(listeStreetArt.get(postionliste).getGeocoordinates().getLat()));
    }

    @Override
    public void onClick(View v) {
        Log.d("menuupdate", "onClick");

        switch (v.getId()) {
            case R.id.bt_menuBsdUpdate_precedentitem:
                if (postionliste != 0) {
                    postionliste = postionliste - 1;

                    Afficherdonnee();
                } else {
                    Toast.makeText(getContext(), "Debut du fichier", Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.bt_menuBsdUpdate_suivantitem:

                if (!postionliste.equals(maxIdListeSteetArt - 1)) {

                    postionliste = postionliste + 1;
                    Afficherdonnee();
                } else {
                    Toast.makeText(getContext(), "Fin du fichier", Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.bt_menuBsdUpdate_modifierbsd:
                Modifierbsd(listeStreetArt);

                break;
            case R.id.bt_menuBsdUpdate_affichageLatLongGps:

                TrouveCoordonnees();
                break;
            case R.id.bt_menuBsdUpdate_del_bsd:

                EffacerStreetart();
                break;
        }
    }

    private void EffacerStreetart() {

        dao.openWritable();
        dao.delete(listeStreetArt.get(postionliste).getId());
        dao.close();
        MiseAJourListeStreetArt();

    }


    private void TrouveCoordonnees() {


        if (!isGPS) {

            Toast.makeText(getContext(), "Please turn on GPS", Toast.LENGTH_SHORT).show();
            return;
        }
        isContinue = false;
        getLocation();
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    AppConstants.LOCATION_REQUEST);

        } else {
            if (isContinue) {
                mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
            } else {
                mFusedLocationClient.getLastLocation().addOnSuccessListener(getActivity(), location -> {
                    if (location != null) {
                        wayLatitude = location.getLatitude();
                        wayLongitude = location.getLongitude();
                        etlatitude.setText(String.valueOf(wayLatitude));
                        etlongitude.setText(String.valueOf(wayLongitude));
                    } else {
                        mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
                    }
                });
            }
        }
    }


    private void Modifierbsd(List<StreetArt> listeStreetArt) {
        dao.openWritable();
        long idStreetArt = listeStreetArt.get(postionliste).getId();
//shift f6 modifier var
        Double latitude = Double.parseDouble(etlatitude.getText().toString());
        Double longitude = Double.parseDouble(etlongitude.getText().toString());
        Photo photo = new Photo();
        Geocoordinates coordonees = new Geocoordinates(latitude, longitude);
        Object nomlieux = etnomlieux.getText().toString();
        String categories = etCategories.getText().toString();
        StreetArt streetartitem = new StreetArt(nomlieux, "", etadresse.getText().toString(), coordonees, categories, photo);
        dao.update(idStreetArt, streetartitem);
        dao.close();
        MiseAJourListeStreetArt();

    }


    private void Afficherdonnee() {
        tvidbsd.setText(String.valueOf(postionliste));
        etnomlieux.setText(String.valueOf(listeStreetArt.get(postionliste).getNameOfTheWork()));
        etadresse.setText(String.valueOf(listeStreetArt.get(postionliste).getAdresse()));
        etlongitude.setText(String.valueOf(listeStreetArt.get(postionliste).getGeocoordinates().getLon()));
        etlatitude.setText(String.valueOf(listeStreetArt.get(postionliste).getGeocoordinates().getLat()));

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap; // Le pointeur vers notre map
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        map.setMyLocationEnabled(true);
        // map.setInfoWindowAdapter(this);
        LatLng Art = new LatLng(listeStreetArt.get(postionliste).getGeocoordinates().getLat(), listeStreetArt.get(postionliste).getGeocoordinates().getLon());
        map.addMarker(new MarkerOptions().position(Art).title("Street art " + listeStreetArt.get(postionliste).getNameOfTheWork().toString()));
        map.moveCamera(CameraUpdateFactory.newLatLng(Art));
        map.setMinZoomPreference(25);
        map.setMaxZoomPreference(15);
        map.animateCamera(CameraUpdateFactory.zoomTo(15));


        // Test de création d'un tracé
        final PolylineOptions polylines = new PolylineOptions();
        polylines.color(Color.BLUE);
        //On construit le polyline

        LatLng latlong = new LatLng(listeStreetArt.get(postionliste).getGeocoordinates().getLat(), listeStreetArt.get(postionliste).getGeocoordinates().getLon());
        lstLatLng.add(latlong);
        latlong = new LatLng(listeStreetArt.get(0).getGeocoordinates().getLat(), listeStreetArt.get(0).getGeocoordinates().getLon());

        lstLatLng.add(latlong);

        for (final LatLng latLng : lstLatLng) {
            polylines.add(latLng);
        }

        final MarkerOptions markerA = new MarkerOptions();
        markerA.position(lstLatLng.get(0));
        markerA.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        //On déclare un marker rouge que l'on mettra sur l'arrivée
        final MarkerOptions markerB = new MarkerOptions();
        markerB.position(lstLatLng.get(lstLatLng.size() - 1));
        markerB.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        //On met à jour la carte
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(lstLatLng.get(0), 10));
        map.addMarker(markerA);
        map.addPolyline(polylines);
        map.addMarker(markerB);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1000: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (isContinue) {
                        mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
                    } else {
                        mFusedLocationClient.getLastLocation().addOnSuccessListener(getActivity(), location -> {
                            if (location != null) {
                                wayLatitude = location.getLatitude();
                                wayLongitude = location.getLongitude();
                                etlatitude.setText(String.valueOf(wayLatitude));
                                etlongitude.setText(String.valueOf(wayLongitude));
                            } else {
                                mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
                            }
                        });
                    }
                } else {
                    Toast.makeText(getContext(), "Please turn on GPS", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    private void updatespinner() {
        //        SpinnerAdapter adapter
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_spinner_item,
                android.R.id.text1,
                choiceCategories
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spListeDatabase.setAdapter(spinnerAdapter);
        // etCategories.setText(spListeDatabase.getSelectedItem().toString());
        // spListeDatabase.setSelection();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == AppConstants.GPS_REQUEST) {
                isGPS = true; // flag maintain before get location
            }
        }
    }
}
