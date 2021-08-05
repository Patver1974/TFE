package com.example.tft_jeu;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.example.tft_jeu.db.dao.StreetDao;
import com.example.tft_jeu.fragmentJeu.FramJeuAvecMap;
import com.example.tft_jeu.models.StreetArt;
import com.example.tft_jeu.fragmentJeu.FramJeuPageInitial;
import com.example.tft_jeu.fragmentJeu.FramJeuSansMaps;

import java.util.ArrayList;
import java.util.List;

public class PageJeuParametre extends AppCompatActivity {

    Spinner spAfficherListe;
    RadioButton rbouimap, rbnonmap, rbfacile, rbmoyen, rbdifficile;
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
        List<String> categories= new ArrayList<>();
        categories.add("Toutes les categories");
        categories = dao.getAllCategories();
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


        getSupportFragmentManager().beginTransaction()

                .add(R.id.container_fragment, FramJeuPageInitial.class, null)
                .commit();

        String listeAAfficher = spAfficherListe.getSelectedItem().toString();
        Log.d("SPINNER", listeAAfficher);



        StreetDao dao2 = new StreetDao(this);

        if (listeAAfficher.equals("Toutes les categories")){
            dao2.openReadable();
            List<StreetArt> ListeStreetArt = dao2.getAll();
            dao2.close();
        }
        else
        {

            dao2.openReadable();
            List<StreetArt> ListeStreetArt = dao2.getWithWhereCategorie(listeAAfficher);
            dao2.close();
        }


        dao.close();

btNextArt.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if (rbouimap.isChecked()) {
            ouvertureFragmentAvecMap();
        } else {
            ouvertureFragmentSansMap();
        }
    }

});




    }
    private void ouvertureFragmentAvecMap() {
        ///getSupportFragmentManager().beginTransaction()

              //  .replace(R.id.container_fragment, FramJeuAvecMap.class, null)
            //    .commit();


    }
    private void ouvertureFragmentSansMap() {
    //    getSupportFragmentManager().beginTransaction()

      //          .replace(R.id.container_fragment, FramJeuSansMaps.class, null)
        //        .commit();


    }


}