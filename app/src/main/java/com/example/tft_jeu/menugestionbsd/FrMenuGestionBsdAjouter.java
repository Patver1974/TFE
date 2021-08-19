package com.example.tft_jeu.menugestionbsd;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.example.tft_jeu.R;
import com.example.tft_jeu.db.dao.StreetDao;
import com.example.tft_jeu.models.Geocoordinates;
import com.example.tft_jeu.models.Photo;
import com.example.tft_jeu.models.StreetArt;
import com.example.tft_jeu.helpergps.AppConstants;
import com.example.tft_jeu.helpergps.GpsUtils;

import com.google.android.gms.location.LocationServices;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FrMenuGestionBsdAjouter#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FrMenuGestionBsdAjouter extends Fragment {

    Spinner spListeDatabase;
    private EditText etNomLieux, etLatitude, etLongitude, etAdresse, etCategories;
    private Button btClear, btAjouter, btafficherLatLongGps;
    StreetDao dao;
    List<String> choiceCategories;

    private FusedLocationProviderClient mFusedLocationClient;
    private boolean isContinue = false;
    private boolean isGPS = false;
    private double wayLatitude = 0.0, wayLongitude = 0.0;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private Spinner spAfficherListe;


    public FrMenuGestionBsdAjouter() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FrMenuGestionBsdAjouter.
     */
    // TODO: Rename and change types and number of parameters
    public static FrMenuGestionBsdAjouter newInstance() {
        FrMenuGestionBsdAjouter fragment = new FrMenuGestionBsdAjouter();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fr_menu_gestion_bsd_ajouter, container, false);


        dao = new StreetDao(getContext());
        dao.openReadable();
        choiceCategories = dao.getAllCategories();
        dao.close();
        spListeDatabase = v.findViewById(R.id.sp_menubsdAdd_listecategories);
        btAjouter = v.findViewById(R.id.bt_menubsdAdd_ajouteritem);
        btClear = v.findViewById(R.id.bt_menubsdAdd_effaceedonnee);
        etNomLieux = v.findViewById(R.id.et_menubsdAdd_nomlieux);
        etLatitude = v.findViewById(R.id.et_menubsdAdd_coordLatitude);
        etLongitude = v.findViewById(R.id.et_menubsdAdd_coordLongitude);
        etAdresse = v.findViewById(R.id.et_menubsdAdd_adresselieux);
        etCategories = v.findViewById(R.id.et_menubsdAdd_categorie);

        btafficherLatLongGps = v.findViewById(R.id.bt_menubsdAdd_affichageLatLongGps);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        String categorie = "";
        updatespinner();

        new GpsUtils(getContext()).turnGPSOn(new GpsUtils.onGpsListener() {
            @Override
            public void gpsStatus(boolean isGPSEnable) {
                // turn on GPS
                isGPS = isGPSEnable;
            }
        });


        btAjouter.setOnClickListener(view -> {
            Object nom = etNomLieux.getText().toString();
            String adresse = etAdresse.getText().toString();
            Double latitude = Double.parseDouble(etLatitude.getText().toString());
            Double longitude = Double.parseDouble(etLongitude.getText().toString());
            Geocoordinates coodonnee = new Geocoordinates(latitude, longitude);
            Photo photo = new Photo();
            photo.setFilename("name");
if (!nom.equals("") && !coodonnee.getLat().toString().equals("")  && !coodonnee.getLon().toString().equals("") && etCategories.getText().toString().equals("")) {
            StreetArt streetart = new StreetArt(nom, "", adresse, coodonnee, etCategories.getText().toString(), photo);
            StreetDao streetDao = new StreetDao(v.getContext());
            streetDao.openWritable();
            streetDao.insert(streetart);
            streetDao.close();
            cleardonnees();}
else {
    StringBuilder sbMsgControle=new StringBuilder("CONTROLE : \n");
    sbMsgControle.append( "Le nom ") ;
    if (etNomLieux.getText().toString().equals("")){sbMsgControle.append("n");}
    sbMsgControle.append("a été fourni. \n");
    sbMsgControle.append( "La categorie ") ;
    if (etCategories.getText().toString().equals("")){sbMsgControle.append("n");}
    sbMsgControle.append("a été fournie. \n");
    sbMsgControle.append( "La latitude ") ;
     if (coodonnee.getLat().toString().equals("")){sbMsgControle.append("n");}
    sbMsgControle.append("a été fournie. \n");
    sbMsgControle.append( "La longitude  ") ;
    if (coodonnee.getLon().toString().equals("")){sbMsgControle.append("n");}
    sbMsgControle.append("a été fournie. \n");
    Toast.makeText(getContext(),sbMsgControle,Toast.LENGTH_LONG);
}
        });
        btafficherLatLongGps.setOnClickListener(view -> {
                    if (!isGPS) {

                        Toast.makeText(getContext(), "Please turn on GPS", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    isContinue = false;
                    getLocation();
                }
        );

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

    private void cleardonnees() {
        etAdresse.setText("");
        etLatitude.setText("");
        etLongitude.setText("");
        etNomLieux.setText("");
        etCategories.setText("");

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

                        etLatitude.setText(String.valueOf(wayLatitude));
                        etLongitude.setText(String.valueOf(wayLongitude));
                    } else {
                        mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
                    }
                });
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
                                etLatitude.setText(String.valueOf(wayLatitude));
                                etLongitude.setText(String.valueOf(wayLongitude));
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


