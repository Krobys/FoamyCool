package com.akrivonos.beerdictionaryapplication.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.akrivonos.beerdictionaryapplication.R;

public class SearchBeerNameFragment extends Fragment {


    public SearchBeerNameFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_beer_name, container, false);
    }

}
