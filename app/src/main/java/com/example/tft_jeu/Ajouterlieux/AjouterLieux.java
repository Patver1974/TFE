package com.example.tft_jeu.Ajouterlieux;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tft_jeu.R;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class AjouterLieux extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_lieux);
        Spinner spListeDatabase;
        TextView tvNomLieux, tvLatitude, tvLongitude;
        EditText etNomLieux, etLatitude, etLongitude;
        Button btClear, btAjouter;

        spListeDatabase = findViewById(R.id.sp_afficherliste_liste);
        tvNomLieux = findViewById(R.id.tv_ajouterlieux_nomlieux);
        tvLatitude = findViewById(R.id.tv_ajouterlieux_latitude);
        tvLongitude = findViewById(R.id.tv_ajouterlieux_longitude);
        btAjouter = findViewById(R.id.bt_ajouterlieux_ajouteritem);
        btClear = findViewById(R.id.bt_ajouterlieux_effaceedonnee);

    }
}