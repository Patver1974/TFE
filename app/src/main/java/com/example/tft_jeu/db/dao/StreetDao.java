package com.example.tft_jeu.db.dao;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.tft_jeu.db.DbInfo;
import  com.example.tft_jeu.models.Geocoordinates;
import  com.example.tft_jeu.models.StreetArt;

import java.util.ArrayList;
import java.util.List;

public class StreetDao extends DaoBase<StreetArt> {
    public StreetDao(Context context) {
        super(context);
    }



    // Méthode du CRUD
    private ContentValues generateContentValues(StreetArt streetArt) {
        ContentValues cv = new ContentValues();
        cv.put(DbInfo.ArtStreetTable.COLUMN_NAME,streetArt.getNameOfTheWork() == null ? "": streetArt.getNameOfTheWork().toString());
        cv.put(DbInfo.ArtStreetTable.COLUMN_LATITUDE, streetArt.getGeocoordinates().getLat());
        cv.put(DbInfo.ArtStreetTable.COLUMN_LONGITUDE, streetArt.getGeocoordinates().getLon() );
        cv.put(DbInfo.ArtStreetTable.COLUMN_ADRESSE,streetArt.getAdresse() == null ? "" : streetArt.getAdresse());
        cv.put(DbInfo.ArtStreetTable.COLUMN_CATEGORIE, streetArt.getCategorie());
        //cv.put(DbInfo.ArtStreetTable.COLUMN_CATEGORIE

//        ContentValues contentValues = new ContentValues();
//        contentValues.put(DbInfo.ArtStreetTable.COLUMN_NAME, (byte[]) streetArt.getNameOfTheWork());

        return cv;
    }

    private StreetArt cursorToData(Cursor cursor) {

        long id = cursor.getLong(cursor.getColumnIndex(DbInfo.ArtStreetTable.COLUMN_ID));
        String name = cursor.getString(cursor.getColumnIndex(DbInfo.ArtStreetTable.COLUMN_NAME));
        String nameauthor = cursor.getString(cursor.getColumnIndex(DbInfo.ArtStreetTable.COLUMN_NAMEAUTHOR));
        Geocoordinates coodonnee = new Geocoordinates(cursor.getDouble(cursor.getColumnIndex(DbInfo.ArtStreetTable.COLUMN_LATITUDE)),cursor.getDouble(cursor.getColumnIndex(DbInfo.ArtStreetTable.COLUMN_LONGITUDE)));
        String categorie = cursor.getString(cursor.getColumnIndex(DbInfo.ArtStreetTable.COLUMN_CATEGORIE));
        return new StreetArt(id, name,nameauthor,coodonnee, categorie);

    }

    // - Create
    public long insert(StreetArt streetArt) {
        ContentValues cv = generateContentValues(streetArt);

        return db.insert(DbInfo.ArtStreetTable.TABLE_NAME, null, cv);
    }

    // - Read
    public StreetArt get(long id) {
        String whereClause = DbInfo.ArtStreetTable.COLUMN_ID + " = ?";
        String[] whereArg = new String[] {
                String.valueOf(id)
        };

        // Création d'un curseur qui permet d'obtenir le resultat d'un select
        Cursor cursor = db.query(DbInfo.ArtStreetTable.TABLE_NAME, null, whereClause, whereArg, null, null, null);

        // Tester si on a un resultat
        if(cursor.getCount() == 0) {
            return null;
        }

        // On déplace le curseur sur le resutlat
        cursor.moveToFirst();

        // On lire les données pointée par le curseur
        StreetArt result = cursorToData(cursor);

        // On cloture le curseur
        cursor.close();

        return result;
    }

    public List<StreetArt> getAll() {
        // Création d'un curseur qui permet d'obtenir le resultat d'un select
        Cursor cursor = db.query(DbInfo.ArtStreetTable.TABLE_NAME, null, null, null, null, null, null);

        // Initialise la liste de resultat
        List<StreetArt> results = new ArrayList<>();

        // Verification qu'il y a un resultat
        if(cursor.getCount() == 0) {
            return results; // Liste vide
        }

        // On place le curseur sur le premier resultat
        cursor.moveToFirst();

        while(! cursor.isAfterLast()) {  // On continue tant qu'on a pas fait toute les resultats

            // On extrait les données du curseur
            StreetArt cat = cursorToData(cursor);
            results.add(cat);

            // On passe à la prochain valeur de resultat
            cursor.moveToNext();
        }

        // On cloture le curseur
        cursor.close();

        // On renvoie les resultats
        return results;
    }

    // - Update
    public boolean update(long id, StreetArt data) {
        ContentValues cv = generateContentValues(data);

        String whereClause = DbInfo.ArtStreetTable.COLUMN_ID + " = ?";
        String[] whereArg = new String[] {
                String.valueOf(id)
        };

        int nbRow = db.update(DbInfo.ArtStreetTable.TABLE_NAME, cv, whereClause, whereArg);
        return nbRow == 1;
    }

    // - Delete
    public boolean delete(long id) {
        String whereClause = DbInfo.ArtStreetTable.COLUMN_ID + " = ?";
        String[] whereArg = new String[] {
                String.valueOf(id)
        };

        int nbRow = db.delete(DbInfo.ArtStreetTable.TABLE_NAME, whereClause, whereArg);
        return nbRow == 1;
    }
    public List<String> getAllCategories() {
        // Création d'un curseur qui permet d'obtenir le resultat d'un select
        Cursor cursor = db.query(
                true,
                DbInfo.ArtStreetTable.TABLE_NAME,
                new String[] { DbInfo.ArtStreetTable.COLUMN_CATEGORIE },
                null,
                null,
                null,
                null,
                null,
                null);


        // Initialise la liste de resultat
        List<String> results = new ArrayList<>();

        // Verification qu'il y a un resultat
        if(cursor.getCount() == 0) {
            return results; // Liste vide
        }

        // On place le curseur sur le premier resultat
        cursor.moveToFirst();

        while(! cursor.isAfterLast()) {  // On continue tant qu'on a pas fait toute les resultats

            // On extrait les données du curseur
            String cat = cursor.getString(cursor.getColumnIndex(DbInfo.ArtStreetTable.COLUMN_CATEGORIE));
            results.add(cat);

            // On passe à la prochain valeur de resultat
            cursor.moveToNext();
        }

        // On cloture le curseur
        cursor.close();

        // On renvoie les resultats
        return results;
    }

    public List<StreetArt> getWithWhereCategorie(String colonneCategories) {
        // Création d'un curseur qui permet d'obtenir le resultat d'un select
        Cursor cursor = db.query(DbInfo.ArtStreetTable.TABLE_NAME, null,
                DbInfo.ArtStreetTable.COLUMN_CATEGORIE  +" = ?", new String[]{colonneCategories},
        null, null, null);

        // Initialise la liste de resultat
        List<StreetArt> results = new ArrayList<>();

        // Verification qu'il y a un resultat
        if(cursor.getCount() == 0) {
            return results; // Liste vide
        }

        // On place le curseur sur le premier resultat
        cursor.moveToFirst();

        while(! cursor.isAfterLast()) {  // On continue tant qu'on a pas fait toute les resultats

            // On extrait les données du curseur
            StreetArt cat = cursorToData(cursor);
            results.add(cat);

            // On passe à la prochain valeur de resultat
            cursor.moveToNext();
        }

        // On cloture le curseur
        cursor.close();

        // On renvoie les resultats
        return results;
    }
}


