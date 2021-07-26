package com.example.tft_jeu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.tft_jeu.jsonStreetArt.StreetArtApi;
import com.example.tft_jeu.test.MainActivityTestMap;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {



    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        // Récupère les fragments
        MainListeFrag mainListeFrag = MainListeFrag.newInstance();
        FragJeuStreetArt fragJeuStreetArt= FragJeuStreetArt.newInstance();

        // Affiche le fragment initial (station)
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction initialTransaction = fm.beginTransaction();
        initialTransaction.replace(R.id.fl_main_fragment_container, mainListeFrag);
        initialTransaction.commit();

        // Setup la bottom navigation view pour lancer les différents fragments
        bottomNavigationView = findViewById(R.id.bottom_nav_main);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {

            if (item.getItemId() == R.id.menu_listeart) {

                FragmentTransaction transactionToListeFragment = fm.beginTransaction();

                transactionToListeFragment.setCustomAnimations(
                        android.R.anim.fade_in,
                        android.R.anim.fade_out,
                        android.R.anim.fade_in,
                        android.R.anim.fade_out
                );

                transactionToListeFragment.replace(R.id.fl_main_fragment_container, mainListeFrag);

                transactionToListeFragment.commit();

                return true;
            }
            else if (item.getItemId() == R.id.menu_jeu){

                FragmentTransaction transactionToJeuFragment = fm.beginTransaction();

                transactionToJeuFragment.setCustomAnimations(
                        android.R.anim.fade_in,
                        android.R.anim.fade_out,
                        android.R.anim.fade_in,
                        android.R.anim.fade_out
                );

                transactionToJeuFragment.replace(R.id.fl_main_fragment_container, fragJeuStreetArt);


                transactionToJeuFragment.commit();

                return true;
            }
            else {
                return false;
            }

        });
        Button btgo;
        btgo = findViewById(R.id.bt_main_go);
        btgo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivityTestMap.class);
                startActivity(intent);
            }
        });


    }
}
