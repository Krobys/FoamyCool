package com.akrivonos.beerdictionaryapplication.fragments;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.akrivonos.beerdictionaryapplication.R;
import com.akrivonos.beerdictionaryapplication.adapters.BreweryAdapter;
import com.akrivonos.beerdictionaryapplication.interfaces.MoveToDetailsBreweryListener;

public class SearchGeoBreweryFragment extends Fragment {

    private BreweryAdapter breweryAdapter;
    private RecyclerView recyclerViewBrewery;

    public SearchGeoBreweryFragment() {
        // Required empty public constructor
    }
    //
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MoveToDetailsBreweryListener moveToDetailsBreweryListener = (MoveToDetailsBreweryListener) getActivity();
        breweryAdapter = new BreweryAdapter(getContext(), moveToDetailsBreweryListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_geo_brewery, container, false);
        recyclerViewBrewery = view.findViewById(R.id.recycle_view_brewery);
        recyclerViewBrewery.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewBrewery.setAdapter(breweryAdapter);

        //TODO Make request
        return view;
    }

}
