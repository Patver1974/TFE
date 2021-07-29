package com.example.tft_jeu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tft_jeu.Ajouterlieux.AjouterLieux;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button BtAffichageListe,BtJeu,BtAjouterItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BtAffichageListe = findViewById(R.id.Bt_Main_ListeAvecMap);
        BtJeu = findViewById(R.id.Bt_Main_Jeu);
        BtAjouterItem = findViewById(R.id.Bt_Main_Ajouter_item);

        BtAffichageListe.setOnClickListener(this);
        BtJeu.setOnClickListener(this);
        BtAjouterItem.setOnClickListener(this);
    }
    @Override
    public void onClick(View v){
        switch(v.getId()) {
            case R.id.Bt_Main_ListeAvecMap:
                RunPageListeAvecMap();
                break;

            case R.id.Bt_Main_Jeu:
                RunPageJeu();
                break;

            case R.id.Bt_Main_Ajouter_item:
                RunAjouterItem();
                break;
        }
    }

    private void RunAjouterItem() {
        Intent intentList = new Intent(getApplicationContext(), AjouterLieux.class);
        startActivity(intentList);
        finish();
    }

    private void RunPageJeu() {
        Intent intentList = new Intent(getApplicationContext(), AfficherListeStreet.class);
        startActivity(intentList);
        finish();
    }

    private void RunPageListeAvecMap() {
        Intent intentList = new Intent(getApplicationContext(), AfficherListeStreet.class);
        startActivity(intentList);
        finish();
    }
}