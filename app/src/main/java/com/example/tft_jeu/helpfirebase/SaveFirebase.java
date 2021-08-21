package com.example.tft_jeu.helpfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.tft_jeu.R;
import com.example.tft_jeu.db.dao.StreetDao;
import com.example.tft_jeu.models.StreetArt;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class SaveFirebase extends AppCompatActivity implements View.OnClickListener {
    private List<StreetArt> listeStreet;
    private StreetDao dao;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_firebase);
Button btread,btsave;
btread = findViewById(R.id.bt_firebase_read);
        btsave = findViewById(R.id.bt_firebase_save);
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

        mDatabase.child("users").get()
                .addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        // userString[0] = dataSnapshot.getValue().toString();
                       // String abc = dataSnapshot.child("StreetArt").getValue().toString();
                        //oobj = new StreetArt (dataSnapshot.child("userId1").getValue().toString(),dataSnapshot.child("userId2").getValue().toString(),dataSnapshot.child("userId3").getValue().toString());
                        //tvread.setText(abc);
                    }
                });








    }


    public void Writefirebase(){
        // Write a message to the database
        //FirebaseDatabase database = FirebaseDatabase.getInstance();
        //DatabaseReference myRef = database.getReference("message");

        dao = new StreetDao(this);
        dao.openReadable();
        listeStreet = dao.getAll();
        dao.close();
        mDatabase = FirebaseDatabase.getInstance("https://exemplefirebase-1423b-default-rtdb.europe-west1.firebasedatabase.app/").getReference();
        mDatabase.child("nbrStreetArt").setValue(String.valueOf(listeStreet.size()));
        for(StreetArt street : listeStreet ) {
            mDatabase.child("StreetArt").child(String.valueOf((street).getId())).child("adresse").setValue(street.getAdresse());
            mDatabase.child("StreetArt").child(String.valueOf((street).getId())).child("nom").setValue(street.getNameOfTheWork());
            mDatabase.child("StreetArt").child(String.valueOf((street).getId())).child("latitude").setValue(street.getGeocoordinates().getLat());
            mDatabase.child("StreetArt").child(String.valueOf((street).getId())).child("longitude").setValue(street.getGeocoordinates().getLon());
            mDatabase.child("StreetArt").child(String.valueOf((street).getId())).child("categorie").setValue(street.getCategorie());
            mDatabase.child("StreetArt").child(String.valueOf((street).getId())).child("id").setValue(street.getId());
        }

    }
}