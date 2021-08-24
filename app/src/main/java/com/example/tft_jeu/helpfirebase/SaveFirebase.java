package com.example.tft_jeu.helpfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tft_jeu.R;
import com.example.tft_jeu.db.dao.StreetDao;
import com.example.tft_jeu.models.Geocoordinates;
import com.example.tft_jeu.models.StreetArt;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SaveFirebase extends AppCompatActivity implements View.OnClickListener {
    private List<StreetArt> listeStreet=new ArrayList<>();
    private StreetArt streetartone;
    private Geocoordinates geocoordinates;
    private StreetDao dao;
    private DatabaseReference mDatabase;
    private Integer nbrStreet;
    TextView tvessai,tvcommentread;
    private StringBuilder stringbuiderstring = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_firebase);
        Button btread, btsave;
        btread = findViewById(R.id.bt_firebase_read);
        btsave = findViewById(R.id.bt_firebase_save);
tvcommentread = findViewById(R.id.tv_firebase_commentread);
        tvessai = findViewById(R.id.tv_firebase_text);


        btread.setOnClickListener(this);
        btsave.setOnClickListener(this);


    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.bt_firebase_read:
                ReadFirebase();
                break;
            case R.id.bt_firebase_save:
                Writefirebase();
                break;
        }
    }

    private void ReadFirebase() {
        mDatabase = FirebaseDatabase.getInstance("https://exemplefirebase-1423b-default-rtdb.europe-west1.firebasedatabase.app/").getReference();
        //  final String[] userString = new String[1];

        mDatabase.child("nbrStreetArt").get()
                .addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {

                        // userString[0] = dataSnapshot.getValue().toString();
                        // String abc = dataSnapshot.child("StreetArt").getValue().toString();
                        //oobj = new StreetArt (dataSnapshot.child("userId1").getValue().toString(),dataSnapshot.child("userId2").getValue().toString(),dataSnapshot.child("userId3").getValue().toString());
                        //tvread.setText(abc);
                        nbrStreet = Integer.valueOf(Objects.requireNonNull(dataSnapshot.getValue()).toString());
                    }
                });
        mDatabase.child("StreetArt").get()
                .addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        Object nomObj;

                        for (int i = 1; i < nbrStreet + 1; i++) {

                            nomObj = String.valueOf(dataSnapshot.child(String.valueOf(i)).child("nom").getValue());
                            geocoordinates = new Geocoordinates(Double.valueOf(String.valueOf(dataSnapshot.child(String.valueOf(i)).child("latitude").getValue())), Double.valueOf(String.valueOf(dataSnapshot.child(String.valueOf(i)).child("longitude").getValue())));
                            streetartone =
                                    new StreetArt(i,
                                            nomObj,
                                            String.valueOf(dataSnapshot.child(String.valueOf(i)).child("adresse").getValue()),
                                            String.valueOf(dataSnapshot.child(String.valueOf(i)).child("categorie").getValue()),
                                            geocoordinates
                                    );
                            listeStreet.add(streetartone);



                        }
                        tvcommentread.setText("Streetart ajoute : " + listeStreet.size());
                    }
                });


    }


    public void Writefirebase() {
        // Write a message to the database
        //FirebaseDatabase database = FirebaseDatabase.getInstance();
        //DatabaseReference myRef = database.getReference("message");

        dao = new StreetDao(this);
        dao.openReadable();
        listeStreet = dao.getAll();
        dao.close();
        mDatabase = FirebaseDatabase.getInstance("https://exemplefirebase-1423b-default-rtdb.europe-west1.firebasedatabase.app/").getReference();
        mDatabase.child("nbrStreetArt").setValue(String.valueOf(listeStreet.size()));
        for (StreetArt street : listeStreet) {
            mDatabase.child("StreetArt").child(String.valueOf((street).getId())).child("adresse").setValue(street.getAdresse());
            mDatabase.child("StreetArt").child(String.valueOf((street).getId())).child("nom").setValue(street.getNameOfTheWork());
            mDatabase.child("StreetArt").child(String.valueOf((street).getId())).child("latitude").setValue(street.getGeocoordinates().getLat());
            mDatabase.child("StreetArt").child(String.valueOf((street).getId())).child("longitude").setValue(street.getGeocoordinates().getLon());
            mDatabase.child("StreetArt").child(String.valueOf((street).getId())).child("categorie").setValue(street.getCategorie());
            mDatabase.child("StreetArt").child(String.valueOf((street).getId())).child("id").setValue(street.getId());
        }

    }
}