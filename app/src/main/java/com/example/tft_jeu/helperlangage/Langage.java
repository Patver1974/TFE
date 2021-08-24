package com.example.tft_jeu.helperlangage;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.tft_jeu.AfficherListeStreet;
import com.example.tft_jeu.MainActivity;
import com.example.tft_jeu.R;
public class Langage extends AppCompatActivity {
RadioButton rbfrancais,rbanglais;
RadioGroup radioGrouplangue;
Button Btconfirmer;
String langueStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_langage);
        rbfrancais = findViewById(R.id.rb_langue_francais);
        rbanglais = findViewById(R.id.rb_langue_anglais);
        radioGrouplangue = findViewById(R.id.rggroup_langue);
Btconfirmer = findViewById(R.id.bt_langue_confirmer);


Btconfirmer.setOnClickListener(v -> {
    if (rbanglais.isChecked()){langueStr = "anglais";} else { langueStr = "anglais";}

    //TODO  getactivity

    SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPref.edit();
    editor.putString(getString(R.string.langueregion), langueStr); //enregistre la distance dans sharedpreference
    editor.commit();
});
        radioGrouplangue.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_langue_francais:
                        LocaleHelper.setLocale(getApplicationContext(), "fr-fr");

                            break;
                    case R.id.rb_langue_anglais:

                        LocaleHelper.setLocale(getApplicationContext(), "eng");
                            break;
                }
            }
        });


        LocaleHelper.setLocale(this, "fr-fr");

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intentList = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intentList);
        finish();
    }


}