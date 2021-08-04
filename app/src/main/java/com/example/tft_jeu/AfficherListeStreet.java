package com.example.tft_jeu;

import android.content.Context;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tft_jeu.Adapter.ActiviteAdapters;
import com.example.tft_jeu.Adapter.StreetArtAdapater;
import com.example.tft_jeu.db.dao.StreetDao;
import com.example.tft_jeu.jsonStreetArt.StreetArtApi;
import com.example.tft_jeu.models.StreetArt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AfficherListeStreet extends AppCompatActivity implements View.OnClickListener  {

    private Button btnListe, btGoback;
    private Spinner spAfficherListe;
    private ArrayList<StreetArt> datatache = new ArrayList<>();
    private RecyclerView rvActivite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afficher_liste_street);
        spAfficherListe = findViewById(R.id.sp_afficherliste_liste);
        rvActivite = findViewById(R.id.rv_AfficherlisteStreet_item);

        btGoback = findViewById(R.id.bt_afficherliste_exit);
        btnListe = findViewById(R.id.bt_afficherliste_AfficherListe);

        StreetDao dao = new StreetDao(this);
        dao.openReadable();
        List<String> categories = dao.getAllCategories();
        Log.d("CATEGORIES", categories.size()+ "");
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

        try {
            List<StreetArt> streetArts = StreetArtApi.getStreetArts(this.getResources().openRawResource(R.raw.data));
            LocationManager lManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
            StreetArtAdapater adapater = new StreetArtAdapater(streetArts, this::onStreetArtClickListener,lManager);
            rvActivite.setAdapter(adapater);
            LinearLayoutManager llm = new LinearLayoutManager(this);
            llm.setOrientation(RecyclerView.VERTICAL);
            rvActivite.setLayoutManager(llm);
        } catch (IOException e) {
            e.printStackTrace();
        }



        btGoback.setOnClickListener(this);
        btnListe.setOnClickListener(this);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_afficherliste_AfficherListe:

                afficherrecyclerview();
                break;
            case R.id.bt_afficherliste_exit:
                goExit();
                break;


            default:
                throw new RuntimeException("Bouton non implementé !");
        }
    }

    private void goExit() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
    private void afficherrecyclerview() {

        LocationManager lManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        //recupere value spinnner
        String ListeAAfficher = spAfficherListe.getSelectedItem().toString();
        //cree datache avec la liste souhaitée

        StreetDao dao = new StreetDao(this);
        dao.openReadable();
        List<String> categories = dao.getAllCategories();
        Log.d("CATEGORIES", categories.size()+ "");
        dao.close();






        ActiviteAdapters activiteAdapters = new ActiviteAdapters(
                getApplicationContext(),
                datatache,
                lManager
        );
        if (datatache.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Pas de lieux dans cette liste", Toast.LENGTH_LONG).show();


        }



        rvActivite.setAdapter(activiteAdapters);
        rvActivite.setHasFixedSize(true);




    }

    private void onStreetArtClickListener(View v) {
        String lat = ((TextView)v.findViewById(R.id.item_activite_coordonnee_lat)).getText().toString();
        String lon = ((TextView)v.findViewById(R.id.item_activite_coordonnee_long)).getText().toString();
        String name = ((TextView)v.findViewById(R.id.tv_ajouterlieux_nomlieux)).getText().toString();
        if (name=="Pas de nom"){name="Destination";};
        Log.d("ITEM_CLICKED", "Lat: "+ lat+ "; Lon: "+ lon);

        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("Lat",lat);
        intent.putExtra("Long",lon);
        intent.putExtra("Name",lon);
        startActivity(intent);
    }
}