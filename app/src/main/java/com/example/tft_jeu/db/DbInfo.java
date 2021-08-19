package com.example.tft_jeu.db;

public class DbInfo {

    public static final String DB_NAME = "DbLieuxBxl";

    public static final int DB_VERSION = 2;

    public static class ArtStreetTable {
        // Le nom de la table
        public static final String TABLE_NAME = "streetArt";

        // Les noms des colonnes
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_NAMEAUTHOR = "nameAuthor";
        public static final String COLUMN_ADRESSE = "adresse";
        public static final String COLUMN_LATITUDE = "latitude";
        public static final String COLUMN_LONGITUDE = "longitude";
        public static final String COLUMN_CATEGORIE = "categorie";
        public static final String COLUMN_PHOTOFILENAME = "filenamephoto";



        // Les requetes SQL (DDL)
        public static final String REQUEST_CREATE =
                "CREATE TABLE " + TABLE_NAME + " ( "
                        + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + COLUMN_NAME + " TEXT NOT NULL, "
                        + COLUMN_ADRESSE + " TEXT NOT NULL, "
                        + COLUMN_NAMEAUTHOR + " TEXT NOT NULL, "
                        + COLUMN_LATITUDE + " TEXT NOT NULL, "
                        + COLUMN_LONGITUDE + " TEXT NOT NULL, "
                        + COLUMN_CATEGORIE + " TEXT NOT NULL, "
                        + COLUMN_PHOTOFILENAME + " TEXT  "
                        + ");" ;

        public static final String REQUEST_DROP =
                "DROP TABLE IF EXISTS " + TABLE_NAME + ";" ;

        // Les requetes SQL (DML)
        public static final String REQUEST_ADD_INITIAL_DATA =
                "INSERT INTO " + TABLE_NAME + " (" + COLUMN_NAME + ") VALUES (?) ;";
    }


}





