package com.example.tft_jeu;

import android.content.Intent;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tft_jeu.Adapter.ActiviteAdapters;
import com.example.tft_jeu.Adapter.StreetArtAdapater;
import com.example.tft_jeu.jsonStreetArt.StreetArtApi;
import com.example.tft_jeu.models.StreetArt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AfficherListeStreet extends AppCompatActivity implements View.OnClickListener  {

    private Button btnListe, btnPositionNow, btGoback;
    private ArrayList<StreetArt> datatache = new ArrayList<>();
    private RecyclerView rvActivite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afficher_liste_street);

        rvActivite = findViewById(R.id.rv_AfficherlisteStreet_item);

        try {
            List<StreetArt> streetArts = StreetArtApi.getStreetArts(this.getResources().openRawResource(R.raw.data));
            StreetArtAdapater adapater = new StreetArtAdapater(streetArts, this::onStreetArtClickListener);
            rvActivite.setAdapter(adapater);
            LinearLayoutManager llm = new LinearLayoutManager(this);
            llm.setOrientation(RecyclerView.VERTICAL);
            rvActivite.setLayoutManager(llm);
        } catch (IOException e) {
            e.printStackTrace();
        }

        btnListe = findViewById(R.id.bt_AfficherlisteStreet_afficherliste);
        btnPositionNow = findViewById(R.id.bt_AfficherlisteStreet_position);
        btGoback = findViewById(R.id.bt_AfficherlisteStreet_goback);

        btGoback.setOnClickListener(this);
        btnListe.setOnClickListener(this);
        btnPositionNow.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_AfficherlisteStreet_afficherliste:
                afficherrecyclerview();
                break;
            case R.id.bt_AfficherlisteStreet_goback:
                goExit();
                break;
            case R.id.bt_AfficherlisteStreet_position:
                afficherPosition();
                break;


            default:
                throw new RuntimeException("Bouton non implementé !");
        }
    }
    private void afficherPosition() {

    }
    private void goExit() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
    private void afficherrecyclerview() {


        ActiviteAdapters activiteAdapters = new ActiviteAdapters(
                getApplicationContext(),
                datatache
        );
        if (datatache.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Pas d'evenement aujourd'hui", Toast.LENGTH_LONG).show();


        }

//        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(
//                3, StaggeredGridLayoutManager.VERTICAL
//        );
//        rvActivite.setLayoutManager(layoutManager);

        rvActivite.setAdapter(activiteAdapters);
        rvActivite.setHasFixedSize(true);




    }
    private void onStreetArtClickListener(View v) {
        String lat = ((TextView)v.findViewById(R.id.item_activite_coordonnee_lat)).getText().toString();
        String lon = ((TextView)v.findViewById(R.id.item_activite_coordonnee_long)).getText().toString();
        Log.d("ITEM_CLICKED", "Lat: "+ lat+ "; Lon: "+ lon);
    }
}