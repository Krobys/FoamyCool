package com.akrivonos.beerdictionaryapplication.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.akrivonos.beerdictionaryapplication.R;
import com.akrivonos.beerdictionaryapplication.interfaces.BottomNavigationHideListener;
import com.akrivonos.beerdictionaryapplication.interfaces.MoveBackListener;
import com.akrivonos.beerdictionaryapplication.models.BeerDetailedDescription;
import com.bumptech.glide.Glide;

import static com.akrivonos.beerdictionaryapplication.MainActivity.DETAILED_INFO_BEER;

public class DetailedInfoBeerFragment extends Fragment {

    private TextView categoryBeerTextView;
    private TextView detailedInfoBeer;
    private ImageView imageBeer;
    private MoveBackListener moveBackListener;
    private BottomNavigationHideListener bottomNavigationHideListener;
    public DetailedInfoBeerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        moveBackListener = (MoveBackListener) getActivity();
        bottomNavigationHideListener = (BottomNavigationHideListener) getActivity();
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        bottomNavigationHideListener.hideBottomNavMenu();
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detailed_info_beer, container, false);
        categoryBeerTextView = view.findViewById(R.id.category_text_beer);
        detailedInfoBeer = view.findViewById(R.id.description_text_beer);
        imageBeer = view.findViewById(R.id.image_beer);
        setUpBeerInformation();
        return view;
    }

    void setUpBeerInformation(){
        Bundle bundle = getArguments();
        if(bundle != null){
            BeerDetailedDescription beerDetailedDescription = bundle.getParcelable(DETAILED_INFO_BEER);
            if(beerDetailedDescription != null){
                if(beerDetailedDescription.getIconBigUrl() != null)
                Glide.with(imageBeer)
                        .load(beerDetailedDescription.getIconBigUrl())
                        .into(imageBeer);
                categoryBeerTextView.setText(beerDetailedDescription.getCategoryBeer());
                detailedInfoBeer.setText(beerDetailedDescription.getDescription());
                AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
                if(appCompatActivity != null){
                    ActionBar actionBar = appCompatActivity.getSupportActionBar();
                    if(actionBar != null){
                        actionBar.setTitle(beerDetailedDescription.getNameBeer());
                    }
                }
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        if(appCompatActivity != null){
            ActionBar actionBar = appCompatActivity.getSupportActionBar();
            if(actionBar != null){
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setDisplayShowTitleEnabled(true);
            }
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            moveBackListener.moveBack();
            return true;
        }
        return false;
    }
}
