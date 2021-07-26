package com.example.tft_jeu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.tft_jeu.Adapter.ActiviteAdapters;
import com.example.tft_jeu.models.StreetArt;

import java.util.ArrayList;

public class AfficherListeStreet extends AppCompatActivity implements View.OnClickListener {

    private Button btnListe, btnPositionNow, btGoback;
    private ArrayList<StreetArt> datatache = new ArrayList<>();
    private RecyclerView rvActivite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afficher_liste_street);

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
                //afficherPosition();
                break;


            default:
                throw new RuntimeException("Bouton non implementé !");
        }
    }
    //a faire
   // private afficherPosition(){
    //}
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

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(
                3, StaggeredGridLayoutManager.VERTICAL
        );
        rvActivite.setLayoutManager(layoutManager);

        rvActivite.setAdapter(activiteAdapters);
        rvActivite.setHasFixedSize(true);

       // rvActivite.addOnItemTouchListener(
         //       new EventRecyclerItemClickListener(getApplicationContext(), (view, position) -> {

           //         StreetArt mystreetart = datatache.get(position);
             //       onEventClick(mystreetart);
               // }));
  //  }


}
}