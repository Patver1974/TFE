package com.example.tft_jeu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.example.tft_jeu.db.dao.StreetDao;
import com.example.tft_jeu.models.StreetArt;

import java.util.List;

public class PageJeuParametre extends AppCompatActivity {

    Spinner spAfficherListe;
    RadioButton rbouimap,rbnonmap,rbfacile,rbmoyen,rbdifficile;
    Button btNextArt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_jeu_parametre);
        spAfficherListe = findViewById(R.id.sp_jeu_liste);
        rbouimap = findViewById(R.id.rb_jeu_ouimaps);
        rbnonmap = findViewById(R.id.rb_jeu_nonmaps);
        rbfacile = findViewById(R.id.rb_jeu_difficule_facile);
        rbmoyen = findViewById(R.id.rb_jeu_difficule_moyen);
        rbdifficile = findViewById(R.id.rb_jeu_difficule_difficile);
        btNextArt = findViewById(R.id.bt_jeu_Start);

        StreetDao dao = new StreetDao(this);
        dao.openReadable();
        List<String> categories = dao.getAllCategories();

        dao.close();

//spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                getApplicationContext(),
                android.R.layout.simple_spinner_item,
                android.R.id.text1,
                categories
        );

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spAfficherListe.setAdapter(spinnerAdapter);
        spAfficherListe.setSelection(0);







    }
}