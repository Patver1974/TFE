package com.example.tft_jeu.db.dao;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;
import com.example.tft_jeu.db.DbHelper;
import com.example.tft_jeu.models.StreetArt;

public abstract class DaoBase<TData> {

    private Context context;
    private DbHelper dbHelper;
    protected SQLiteDatabase db;

    public DaoBase(Context context) {
        this.context = context;
    }

    // Gestion de la connexion à la DB via DbHelper
    public DaoBase openWritable() {
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();

        return this;
    }

    public DaoBase openReadable() {
        dbHelper = new DbHelper(context);
        db = dbHelper.getReadableDatabase();

        return this;
    }

    public void close() {
        db.close();
        dbHelper.close();
    }


    // Méthode du CRUD
    public abstract long insert(TData data);
    public abstract TData get(long id);
    public abstract List<TData> getAll();
    public abstract boolean update(long id, TData data);
    public abstract boolean delete(long id);

}

