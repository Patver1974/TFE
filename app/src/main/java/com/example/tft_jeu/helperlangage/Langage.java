package com.example.tft_jeu.helperlangage;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.tft_jeu.helperlangage.LocaleHelper;
import com.example.tft_jeu.AfficherListeStreet;
import com.example.tft_jeu.MainActivity;
import com.example.tft_jeu.R;

public class Langage extends AppCompatActivity {
    //https://www.youtube.com/watch?v=RjswexkneB0
    RadioButton rbfrancais, rbanglais;
    RadioGroup radioGrouplangue;
    Button btconfirmer, btQuitter;
    String langueStr;
    Context context;
    TextView tvMsgAccueil;
    Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_langage);
        rbfrancais = findViewById(R.id.rb_langue_francais);
        rbanglais = findViewById(R.id.rb_langue_anglais);
        radioGrouplangue = findViewById(R.id.rggroup_langue);
        btconfirmer = findViewById(R.id.bt_langue_confirmer);
        tvMsgAccueil = findViewById(R.id.tv_langue_msgaccueil);
        btQuitter = findViewById(R.id.bt_langue_quitter);
        initLangue();

        btQuitter.setOnClickListener(v -> {

            Intent intentList = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intentList);
            finish();
                }
        );
        btconfirmer.setOnClickListener(v -> {

            if (rbfrancais.isChecked()) {
                context = LocaleHelper.setLocale(getApplicationContext(), "fr");
                langueStr = "fr";
                miseAJour();
                quitter();

            }
            if (rbanglais.isChecked()) {
                context = LocaleHelper.setLocale(getApplicationContext(), "en");
                langueStr = "en";
                miseAJour();
                quitter();
            }


        });
        radioGrouplangue.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_langue_francais:
                        context = LocaleHelper.setLocale(getApplicationContext(), "fr");
                        langueStr = "fr";
                        miseAJour();
                        break;
                    case R.id.rb_langue_anglais:
                        context = LocaleHelper.setLocale(getApplicationContext(), "en");
                        langueStr = "en";
                        miseAJour();
                        break;
                }
            }
        });


    }

    private void initLangue() {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        langueStr = prefs.getString("langue", "fr");
        context = LocaleHelper.setLocale(getApplicationContext(), langueStr);
        miseAJour();

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        quitter();
    }

    private void quitter() {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("langue", langueStr);
        editor.apply();


        Intent intentList = new Intent(getApplicationContext(), MainActivity.class);

        startActivity(intentList);
        finish();
    }

    public void miseAJour() {
        Log.d("langue", langueStr);
        resources = context.getResources();
        rbfrancais.setText(resources.getString((R.string.francais)));
        rbanglais.setText(resources.getString((R.string.anglais)));
        btconfirmer.setText(resources.getString((R.string.confirme)));
        btQuitter.setText(resources.getString((R.string.quitter)));
        tvMsgAccueil.setText(resources.getString((R.string.choisir_langue)));


    }
}