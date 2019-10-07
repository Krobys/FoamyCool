package com.akrivonos.beerdictionaryapplication.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.akrivonos.beerdictionaryapplication.BeerModelData;
import com.akrivonos.beerdictionaryapplication.R;
import com.akrivonos.beerdictionaryapplication.adapters.BreweryAdapter;
import com.akrivonos.beerdictionaryapplication.interfaces.BottomNavigationHideListener;
import com.akrivonos.beerdictionaryapplication.interfaces.MoveBackListener;
import com.akrivonos.beerdictionaryapplication.interfaces.MoveToDetailsBreweryListener;
import com.akrivonos.beerdictionaryapplication.interfaces.mvp_listeners.presenter_listeners.BreweriesPresenterListener;
import com.akrivonos.beerdictionaryapplication.interfaces.mvp_listeners.view_control_listeners.BreweryViewListener;
import com.akrivonos.beerdictionaryapplication.models.BreweryDetailedDescription;
import com.akrivonos.beerdictionaryapplication.presenters.BreweryPresenter;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import static com.akrivonos.beerdictionaryapplication.MainActivity.COORDINATES_BREWERIES_SEARCH;

public class SearchGeoBreweryFragment extends Fragment implements BreweryViewListener {

    private BreweryAdapter breweryAdapter;
    private ProgressBar progressBar;
    private LinearLayout noBreweriesMessage;
    private MoveBackListener moveBackListener;
    private BottomNavigationHideListener bottomNavigationHideListener;
    private BreweriesPresenterListener presenterListener;
    public SearchGeoBreweryFragment() {
        // Required empty public constructor
    }

    //
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpAdapterAndListeners();
    }

    private void setUpAdapterAndListeners() {
        Activity activity = getActivity();
        if (activity != null) {
            MoveToDetailsBreweryListener moveToDetailsBreweryListener = (MoveToDetailsBreweryListener) activity;
            bottomNavigationHideListener = (BottomNavigationHideListener) activity;
            moveBackListener = (MoveBackListener) activity;
            breweryAdapter = new BreweryAdapter(getContext(), moveToDetailsBreweryListener);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_geo_brewery, container, false);
        setHasOptionsMenu(true);
        setUpPresenter();
        setUpScreenAndValues(view);
        startLoadingInformation();
        return view;
    }

    private void setUpPresenter(){
        BeerModelData beerModelData = BeerModelData.getInstance(getContext());
        presenterListener = new BreweryPresenter(beerModelData, this);
    }

    private void setUpScreenAndValues(View view) {
        progressBar = view.findViewById(R.id.progressBarBrewery);
        noBreweriesMessage = view.findViewById(R.id.no_breweries_message);
        noBreweriesMessage.setOnClickListener(v -> moveBackListener.moveBack());
        RecyclerView recyclerViewBrewery = view.findViewById(R.id.recycle_view_brewery);
        recyclerViewBrewery.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewBrewery.setAdapter(breweryAdapter);
        if (breweryAdapter.isSet()) {
            progressBar.setVisibility(View.GONE);
        }
        bottomNavigationHideListener.hideBottomNavMenu();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenterListener.detachView();
    }

    private void startLoadingInformation() {
        if (!breweryAdapter.isSet()){
            Bundle bundle = getArguments();
            if (bundle != null) {
                LatLng coordinatesBreweries = bundle.getParcelable(COORDINATES_BREWERIES_SEARCH);
                presenterListener.loadBreweryList(coordinatesBreweries);
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        setUpActionBar();
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void setUpActionBar() {
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        if (appCompatActivity != null) {
            ActionBar actionBar = appCompatActivity.getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setDisplayShowTitleEnabled(true);
                actionBar.setTitle("Breweries on map");
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            moveBackListener.moveBack();
            return true;
        }
        return false;
    }

    @Override
    public void showBreweryList(List<BreweryDetailedDescription> breweryList) {
        breweryAdapter.setData(breweryList);
        breweryAdapter.notifyDataSetChanged();
    }

    @Override
    public void setVisibilityEmptyMessage(int visibility) {
        noBreweriesMessage.setVisibility(visibility);
    }

    @Override
    public void setVisibilityProgressBar(int visibility) {
        progressBar.setVisibility(visibility);
    }
}
