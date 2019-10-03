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

import com.akrivonos.beerdictionaryapplication.R;
import com.akrivonos.beerdictionaryapplication.adapters.BreweryAdapter;
import com.akrivonos.beerdictionaryapplication.interfaces.BottomNavigationHideListener;
import com.akrivonos.beerdictionaryapplication.interfaces.MoveBackListener;
import com.akrivonos.beerdictionaryapplication.interfaces.MoveToDetailsBreweryListener;
import com.akrivonos.beerdictionaryapplication.models.BreweryDetailedDescription;
import com.akrivonos.beerdictionaryapplication.retrofit.RetrofitSearchBeer;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static com.akrivonos.beerdictionaryapplication.MainActivity.COORDINATES_BREWERIES_SEARCH;

public class SearchGeoBreweryFragment extends Fragment {

    private BreweryAdapter breweryAdapter;
    private Disposable observerBreweryDisposable;
    private ProgressBar progressBar;
    private LinearLayout noBreweriesMessage;
    private MoveBackListener moveBackListener;
    private BottomNavigationHideListener bottomNavigationHideListener;
    private final Observer<ArrayList<BreweryDetailedDescription>> observerBrewery = new Observer<ArrayList<BreweryDetailedDescription>>() {
        @Override
        public void onSubscribe(Disposable d) {
            observerBreweryDisposable = d;
        }

        @Override
        public void onNext(ArrayList<BreweryDetailedDescription> beerModels) {
            if (beerModels.size() != 0) {
                breweryAdapter.setData(beerModels);
                breweryAdapter.notifyDataSetChanged();
            } else {
                noBreweriesMessage.setVisibility(View.VISIBLE);
                noBreweriesMessage.setOnClickListener(v -> moveBackListener.moveBack());
            }
            progressBar.setVisibility(View.GONE);
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
        }

        @Override
        public void onComplete() {

        }
    };

    public SearchGeoBreweryFragment() {
        // Required empty public constructor
    }

    //
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Activity activity = getActivity();
        if (activity != null) {
            MoveToDetailsBreweryListener moveToDetailsBreweryListener = (MoveToDetailsBreweryListener) activity;
            bottomNavigationHideListener = (BottomNavigationHideListener) activity;
            moveBackListener = (MoveBackListener) activity;
            breweryAdapter = new BreweryAdapter(getContext(), moveToDetailsBreweryListener);
            startLoadingInformation();
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_geo_brewery, container, false);
        setHasOptionsMenu(true);
        progressBar = view.findViewById(R.id.progressBarBrewery);
        noBreweriesMessage = view.findViewById(R.id.no_breweries_message);
        RecyclerView recyclerViewBrewery = view.findViewById(R.id.recycle_view_brewery);
        recyclerViewBrewery.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewBrewery.setAdapter(breweryAdapter);
        makeObservers();
        setUpScreen();
        return view;
    }

    private void setUpScreen() {
        if (breweryAdapter.isSetted()) {
            progressBar.setVisibility(View.GONE);
        }
        bottomNavigationHideListener.hideBottomNavMenu();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        disposeObservers();
    }

    private void disposeObservers() {
        observerBreweryDisposable.dispose();
    }

    private void makeObservers() {
        RetrofitSearchBeer.getInstance().setObserverBreweries(observerBrewery);
    }

    private void startLoadingInformation() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            LatLng coordinatesBreweries = bundle.getParcelable(COORDINATES_BREWERIES_SEARCH);
            RetrofitSearchBeer.getInstance().startDownloadBreweryList(coordinatesBreweries);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
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
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            moveBackListener.moveBack();
            return true;
        }
        return false;
    }
}
