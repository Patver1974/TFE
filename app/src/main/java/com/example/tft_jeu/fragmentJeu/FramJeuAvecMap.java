package com.example.tft_jeu.fragmentJeu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tft_jeu.R;
import com.example.tft_jeu.models.StreetArt;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FramJeuAvecMap#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FramJeuAvecMap extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private StreetArt streetArt;

    public FramJeuAvecMap() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FramJeuAvecMap.
     */
    // TODO: Rename and change types and number of parameters
    public static FramJeuAvecMap newInstance(String param1, String param2) {
        FramJeuAvecMap fragment = new FramJeuAvecMap();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            streetArt = getArguments().getParcelable("STREET_ART");
           // mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fram_jeu_avec_map, container, false);
    }
}