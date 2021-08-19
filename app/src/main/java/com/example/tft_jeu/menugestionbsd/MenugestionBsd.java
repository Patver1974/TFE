package com.example.tft_jeu.menugestionbsd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.view.View;
import android.os.Bundle;

import com.example.tft_jeu.AfficherListeStreet;
import com.example.tft_jeu.MainActivity;
import com.example.tft_jeu.R;
import com.example.tft_jeu.menugestionbsd.FrMenuGestionBsdAjouter;
import com.example.tft_jeu.menugestionbsd.FrMenuGestionBsdUpdate;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MenugestionBsd extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menugestion_bsd);



        // Récupère les fragments
        FrMenuGestionBsdUpdate frMenuGestionBsdUpdate = FrMenuGestionBsdUpdate.newInstance();
        FrMenuGestionBsdAjouter frMenuGestionBsdAjouter = FrMenuGestionBsdAjouter.newInstance();


        // Affiche le fragment initial (station)
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction initialTransaction = fm.beginTransaction();
        initialTransaction.replace(R.id.fl_gestionbsd_fragment_container, frMenuGestionBsdAjouter);
        initialTransaction.commit();

        // Setup la bottom navigation view pour lancer les différents fragments
        bottomNavigationView = findViewById(R.id.bottom_nav_main);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {

            if (item.getItemId() == R.id.menu_Ajouter) {

                FragmentTransaction gestionbsdFragment = fm.beginTransaction();

                gestionbsdFragment.setCustomAnimations(
                        android.R.anim.fade_in,
                        android.R.anim.fade_out,
                        android.R.anim.fade_in,
                        android.R.anim.fade_out
                );

                gestionbsdFragment.replace(R.id.fl_gestionbsd_fragment_container, frMenuGestionBsdAjouter);

                gestionbsdFragment.commit();

                return true;
            }
            else if (item.getItemId() == R.id.menu_Update){

                FragmentTransaction transactionToFavoritesFragment = fm.beginTransaction();

                transactionToFavoritesFragment.setCustomAnimations(
                        android.R.anim.fade_in,
                        android.R.anim.fade_out,
                        android.R.anim.fade_in,
                        android.R.anim.fade_out
                );

                transactionToFavoritesFragment.replace(R.id.fl_gestionbsd_fragment_container, frMenuGestionBsdUpdate);


                transactionToFavoritesFragment.commit();

                return true;
            }
            else if (item.getItemId() == R.id.menu_menuGeneral){

                Intent intentGoMenuPrincipal = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intentGoMenuPrincipal);
                finish();

                return true;
            }
            else {
                return false;
            }

        });
    }
}