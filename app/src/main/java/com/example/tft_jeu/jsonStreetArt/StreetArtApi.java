package com.example.tft_jeu.jsonStreetArt;

import com.example.tft_jeu.models.StreetArt;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class StreetArtApi {

    public static List<StreetArt> getStreetArts(InputStream file) throws IOException {
        List<StreetArt> streetArts = new ArrayList<StreetArt>();

        BufferedReader reader = new BufferedReader(new InputStreamReader(file));
        StringBuilder builder = new StringBuilder();

        String line = null;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }

        reader.close();
        file.close();


        Gson gson = new Gson();

        JsonArray array = JsonParser.parseString(builder.toString()).getAsJsonArray();

        for (int i = 0; i < array.size(); i++) {
            String json = array.get(i).getAsJsonObject().toString();
            StreetArt streetArt = gson.fromJson(json, StreetArt.class);

            streetArts.add(streetArt);
        }

        return streetArts;
    }
}
