package com.example.tft_jeu.Ajouterlieux;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tft_jeu.R;
import com.example.tft_jeu.db.dao.StreetDao;
import com.example.tft_jeu.models.Geocoordinates;
import com.example.tft_jeu.models.StreetArt;

import android.database.DataSetObserver;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import android.widget.SpinnerAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AjouterLieux extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_lieux);
        Spinner spListeDatabase;

        EditText etNomLieux, etLatitude, etLongitude, etAdresse;
         Button btClear, btAjouter,btafficherLatLongGps;


        spListeDatabase = (Spinner) findViewById(R.id.sp_test);

        StreetDao dao = new StreetDao(this);
        dao.openReadable();
        List<String> choiceCategories = dao.getAllCategories();
        dao.close();
        btAjouter = findViewById(R.id.bt_ajouterlieux_ajouteritem);
        btClear = findViewById(R.id.bt_ajouterlieux_effaceedonnee);
        etNomLieux =  findViewById(R.id.et_ajouterlieux_nomlieux);
        etLatitude = findViewById(R.id.et_ajouterlieux_coordLatitude);
        etLongitude =  findViewById(R.id.et_ajouterlieux_coordLongitude);
        etAdresse =  findViewById(R.id.et_ajouterlieux_adresselieux);
        btafficherLatLongGps =  findViewById(R.id.bt_ajouterlieux_affichageLatLongGps);
        String categorie="";



//        SpinnerAdapter adapter =
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                getApplicationContext(),
                android.R.layout.simple_spinner_item,
                android.R.id.text1,
                choiceCategories
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spListeDatabase.setAdapter(spinnerAdapter);
//        spListeDatabase.setSelection();


        btAjouter.setOnClickListener(v -> {
            Object nom = etNomLieux.getText().toString();
            String adresse = etAdresse.getText().toString();
            Double latitude = Double.parseDouble(etLatitude.getText().toString());
            Double longitude = Double.parseDouble(etLongitude.getText().toString());
            Geocoordinates coodonnee = new Geocoordinates(latitude,longitude);

            StreetArt streetart= new StreetArt(nom,"",adresse,coodonnee, categorie);
            StreetDao streetDao = new StreetDao(getApplicationContext());
            streetDao.openWritable();
            streetDao.insert(streetart);
            streetDao.close();
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedCategorie=parent.getItemAtPosition(position).toString();
        Toast.makeText(this, selectedCategorie,Toast.LENGTH_SHORT);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}