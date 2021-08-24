package com.example.tft_jeu;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import android.view.View;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.tft_jeu.db.dao.StreetDao;
import com.example.tft_jeu.fragmentJeu.FramJeuAvecMap;
import com.example.tft_jeu.models.StreetArt;
import com.example.tft_jeu.fragmentJeu.FramJeuPageInitial;
import com.example.tft_jeu.fragmentJeu.FramJeuSansMaps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class PageJeuParametre extends AppCompatActivity {

    private Spinner spAfficherListe;
    private RadioButton rbouimap, rbnonmap, rbfacile, rbmoyen, rbdifficile;
    private RadioGroup rgdifficulte, rgMapsOuiNon;
    private Button btNextArt,btMenuPrincipal;
    private TextView tvnom,tvadresse;
    private LocationManager lManager;
    private StreetArt streetArtChoisi;
    private List<StreetArt> streetArts;
    private String nomfragment;

    Random random = new Random();
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
        rgdifficulte= findViewById(R.id.grouprb_jeu_difficulte);
        rgMapsOuiNon= findViewById(R.id.grouprb_jeu_maps_ouinon);
        btMenuPrincipal = findViewById(R.id.bt_jeu_GoBack);
        tvnom = findViewById(R.id.tv_jeu_streetnom);
        tvadresse =findViewById(R.id.tv_jeu_streetadress);
        StreetDao dao = new StreetDao(this);
        dao.openReadable();
        List<String> categories= new ArrayList<>();

        categories = dao.getAllCategories();
        dao.close();

        lManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

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

        nomfragment="fragmentinitial";
        getSupportFragmentManager().beginTransaction()

                .add(R.id.container_fragment, FramJeuPageInitial.class, null)
                .commit();

        String listeAAfficher = spAfficherListe.getSelectedItem().toString();




        StreetDao dao2 = new StreetDao(this);

        if (listeAAfficher.equals(getString(R.string.ToutesLesCategories))){
            dao2.openReadable();
            streetArts = dao2.getAll();
            dao2.close();
        }
        else
        {

            dao2.openReadable();
            streetArts = dao2.getWithWhereCategorie(listeAAfficher);
            dao2.close();
        }


        dao.close();

        btNextArt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rbouimap.isChecked()) {
                    streetArtChoisi = choixElementArt();
                    ouvertureFragmentAvecMap(streetArtChoisi);
                } else {
                    streetArtChoisi = choixElementArt();
                    ouvertureFragmentSansMap(streetArtChoisi);
                }
            }

        });

        btMenuPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();

            }

        });

        rgMapsOuiNon.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_jeu_ouimaps:
                        Log.d("pagejeu", "page jeu nom frag actif "+ nomfragment.toString());
                        if (nomfragment.equals("fragmentsansmap")) {
                        ouvertureFragmentAvecMap(streetArtChoisi);
                        break;}
                    case R.id.rb_jeu_nonmaps:
                        Log.d("pagejeu", "page jeu nom frag actif "+ nomfragment.toString());
                        if (nomfragment.equals("fragmentavecmap")) {
                        ouvertureFragmentSansMap(streetArtChoisi);
                        break;}
                }
            }
        });
    }
    @SuppressLint("MissingPermission")
    private StreetArt choixElementArt() {
        ArrayList<StreetArt> ArtwithDistance = new ArrayList<>();
        for(StreetArt str:streetArts){
            Location l = new Location(LocationManager.GPS_PROVIDER);
            l.setLatitude(str.getGeocoordinates().getLat());
            l.setLongitude(str.getGeocoordinates().getLon());
            float dist = lManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).distanceTo(l);
            str.setDistance(dist);
            ArtwithDistance.add(str);}

        Collections.sort(ArtwithDistance, (o1, o2) -> o1.getDistance() < o2.getDistance() ? -1 : 1);


//CHOIX DE L ELEMENT
        int indiceElement = 0;
        int nbrElement =  ArtwithDistance.size();
//difficulte facile je choisi parmi les 3 distances les plus courtes
        if (rbfacile.isChecked() && (nbrElement>3)){
            indiceElement =   random.nextInt(3);
        }
//difficulte moyenne j'enleve les 2 plus grand et le plus petit apres hasard
        if (rbmoyen.isChecked() && (nbrElement>3)) {
            nbrElement= nbrElement-2;

            indiceElement =  1 + random.nextInt(nbrElement);

        }
//difficulte difficile je prend les 50% plus grand et hasard
        if (rbdifficile.isChecked()&& (nbrElement>3)) {
            int nbrElementneg = nbrElement/2;

            indiceElement = nbrElement  - random.nextInt(nbrElementneg);

        }

//si moins de 3 elements dans liste
        if (nbrElement<=3){
            indiceElement =  random.nextInt(nbrElement);

        }

// element choisi
        return ArtwithDistance.get(indiceElement);



    }




    private void ouvertureFragmentAvecMap(StreetArt streetArtChoisi) {
        this.tvnom.setText(streetArtChoisi.getNameOfTheWork().toString());
        this.tvadresse.setText(streetArtChoisi.getAdresse());
        nomfragment="fragmentavecmap";
        Bundle arg = new Bundle();
        arg.putParcelable("STREET_ART", streetArtChoisi);
        getSupportFragmentManager().beginTransaction()

                .replace(R.id.container_fragment, FramJeuAvecMap.class, arg)
                .commit();


    }

    private void ouvertureFragmentSansMap(StreetArt streetArtChoisi) {
        this.tvnom.setText(streetArtChoisi.getNameOfTheWork().toString());
        this.tvadresse.setText(streetArtChoisi.getAdresse());

        nomfragment="fragmentsansmap";
        Bundle arg = new Bundle();
        arg.putParcelable("STREET_ART", streetArtChoisi);
        getSupportFragmentManager().beginTransaction()

                .replace(R.id.container_fragment, FramJeuSansMaps.class, arg)
                .commit();


    }


}
