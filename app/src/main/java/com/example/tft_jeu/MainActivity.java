package com.example.tft_jeu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tft_jeu.db.dao.StreetDao;
import com.example.tft_jeu.jsonStreetArt.StreetArtApi;
import com.example.tft_jeu.models.StreetArt;
import com.example.tft_jeu.menugestionbsd.MenugestionBsd;
import com.example.tft_jeu.helpfirebase.SaveFirebase;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.example.tft_jeu.helperlangage.Langage;
public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button BtAffichageListe,BtJeu,BtAjouterItem,BtFirebase,BtLangue,BtSwipStack;
    private Locale mCurrentLocale;
//net maui new techno
@Override
protected void onStart() {
    super.onStart();

    mCurrentLocale = getResources().getConfiguration().locale;
}



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

BtSwipStack = findViewById(R.id.Bt_Main_swipStack);
        BtAffichageListe = findViewById(R.id.Bt_Main_ListeAvecMap);
        BtJeu = findViewById(R.id.Bt_Main_Jeu);
        BtAjouterItem = findViewById(R.id.Bt_Main_Ajouter_item);
        BtFirebase = findViewById(R.id.Bt_Main_firebase);
        BtLangue = findViewById(R.id.Bt_Main_langue);

        BtAffichageListe.setOnClickListener(this);
        BtJeu.setOnClickListener(this);
        BtAjouterItem.setOnClickListener(this);
        BtFirebase.setOnClickListener(this);
        BtLangue.setOnClickListener(this);
        BtSwipStack.setOnClickListener(this);


        StreetDao dao = new StreetDao(this);

        dao.openReadable();
        StreetArt art = dao.get(1);
        dao.close();
        if (art != null) { return; }
        try {
            List<StreetArt> streetArts = StreetArtApi.getStreetArts(getResources().openRawResource(R.raw.data));
            dao.openWritable();
            for(StreetArt sa: streetArts) {
                sa.setCategorie("Street-Art");
                Log.d("STREET_ART", sa.toString());
                dao.insert(sa);
            }
            dao.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            case R.id.Bt_Main_firebase:
                RunFireBase();
                break;
            case R.id.Bt_Main_langue:
                ChoisirLangue();
                break;
            case R.id.Bt_Main_swipStack:
                SwipStackimg();
                break;
        }
    }

    private void SwipStackimg() {
        Intent intentList = new Intent(getApplicationContext(), Langage.class);
        startActivity(intentList);
        finish();
    }

    private void ChoisirLangue() {
        Intent intentList = new Intent(getApplicationContext(), Langage.class);
        startActivity(intentList);
        finish();
    }

    private void RunFireBase() {
        Intent intentList = new Intent(getApplicationContext(), SaveFirebase.class);
        startActivity(intentList);
        finish();
    }

    private void RunPageListeAvecMap() {
        Intent intentList = new Intent(getApplicationContext(), AfficherListeStreet.class);
        startActivity(intentList);
        finish();
    }
    private void RunPageJeu() {
        Intent intentList = new Intent(getApplicationContext(), PageJeuParametre.class);
        startActivity(intentList);
        finish();
    }
    private void RunAjouterItem() {
        Intent intentList = new Intent(getApplicationContext(), MenugestionBsd.class);
        startActivity(intentList);
        finish();
    }




}