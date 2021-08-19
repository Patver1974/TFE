package com.example.tft_jeu.Ajouterlieux;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tft_jeu.R;
import com.example.tft_jeu.db.dao.StreetDao;
import com.example.tft_jeu.models.StreetArt;

import java.util.ArrayList;
import java.util.List;

public class ModifierLieux extends AppCompatActivity  implements View.OnClickListener{
    private Button btprecedent,btsuivant,btlieuxgps,btmodifier;
    private TextView tvidbsd;
    private EditText etnomlieux,etadresse,etlongitude,etlatitude;
    private List<StreetArt> listeStreetArt = new ArrayList<>();
    private Integer idliste = 0;
    private Integer maxIdListeSteetArt;
    private StreetDao dao = new StreetDao(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier_lieux);





         btprecedent=findViewById(R.id.bt_modiferLieux_precedentitem);
        btsuivant=findViewById(R.id.bt_modiferLieux_suivantitem);
        tvidbsd=findViewById(R.id.tv_modifierlieux_idbsd);
        etnomlieux=findViewById(R.id.et_modifierLieux_nomlieux);
        etadresse=findViewById(R.id.et_modifierlieux_adresselieux);
        etlongitude=findViewById(R.id.et_modifierlieux_coordLongitude);
        etlatitude=findViewById(R.id.et_modifierlieux_coordLatitude);
        btlieuxgps=findViewById(R.id.bt_modifierlieux_affichageLatLongGps);
        btmodifier=findViewById(R.id.bt_modierlieux_modifierbsd);

        btmodifier.setOnClickListener(this);
        btprecedent.setOnClickListener(this);
        btsuivant.setOnClickListener(this);



        dao.openReadable();
        listeStreetArt = dao.getAll();
        dao.close();
        maxIdListeSteetArt =listeStreetArt.size();

       etnomlieux.setText(String.valueOf(listeStreetArt.get(idliste).getId()));
        etadresse.setText(String.valueOf(listeStreetArt.get(idliste).getAdresse()));
        etlongitude.setText(String.valueOf(listeStreetArt.get(idliste).getGeocoordinates().getLon()));
        etlatitude.setText(String.valueOf(listeStreetArt.get(idliste).getGeocoordinates().getLat()));
    }

        @Override
        public void onClick(View v){
            switch(v.getId()) {
                case R.id.bt_modiferLieux_precedentitem:
                    if (idliste!=0) {
                        idliste=idliste-1;
                        Afficherdonnee();
                    }
                    break;

                case R.id.bt_modiferLieux_suivantitem:

                    if (idliste!=maxIdListeSteetArt) {
                        idliste=idliste+1;
                        Afficherdonnee();
                    }
                    break;

                case R.id.bt_modierlieux_modifierbsd:
                    Modifierbsd(listeStreetArt);
                    break;
            }
        }









    private void Modifierbsd(List<StreetArt> listeStreetArt) {
        dao.openWritable();
        StreetArt streetartitem = listeStreetArt.get(idliste);
        dao.update(idliste,streetartitem);
        dao.close();

    }

    private void Afficherdonnee() {
        etnomlieux.setText(String.valueOf(listeStreetArt.get(idliste).getId()));
        etadresse.setText(String.valueOf(listeStreetArt.get(idliste).getAdresse()));
        etlongitude.setText(String.valueOf(listeStreetArt.get(idliste).getGeocoordinates().getLon()));
        etlatitude.setText(String.valueOf(listeStreetArt.get(idliste).getGeocoordinates().getLat()));

    }
}