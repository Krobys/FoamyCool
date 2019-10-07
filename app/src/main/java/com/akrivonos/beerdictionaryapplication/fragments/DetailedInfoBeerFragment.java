package com.akrivonos.beerdictionaryapplication.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.akrivonos.beerdictionaryapplication.BeerModelData;
import com.akrivonos.beerdictionaryapplication.R;
import com.akrivonos.beerdictionaryapplication.interfaces.BottomNavigationHideListener;
import com.akrivonos.beerdictionaryapplication.interfaces.MoveBackListener;
import com.akrivonos.beerdictionaryapplication.interfaces.mvp_listeners.presenter_listeners.DetailedBeerPresenterListener;
import com.akrivonos.beerdictionaryapplication.interfaces.mvp_listeners.view_control_listeners.DetailedBeerViewListener;
import com.akrivonos.beerdictionaryapplication.models.BeerDetailedDescription;
import com.akrivonos.beerdictionaryapplication.presenters.DetailedInfoBeerPresenter;
import com.bumptech.glide.Glide;

import static com.akrivonos.beerdictionaryapplication.MainActivity.DETAILED_INFO_BEER;

public class DetailedInfoBeerFragment extends Fragment implements DetailedBeerViewListener {

    private TextView categoryBeerTextView;
    private TextView detailedInfoBeer;
    private ImageView imageBeer;
    private MoveBackListener moveBackListener;
    private BottomNavigationHideListener bottomNavigationHideListener;
    private BeerDetailedDescription beerDetails;
    private MenuItem favoriteItem;
    private DetailedBeerPresenterListener presenterListener;

    public DetailedInfoBeerFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setUpDatabaseAndListeners();
        super.onCreate(savedInstanceState);
    }

    private void setUpDatabaseAndListeners() {
        Activity activity = getActivity();
        if (activity != null) {
            moveBackListener = (MoveBackListener) activity;
            bottomNavigationHideListener = (BottomNavigationHideListener) activity;
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detailed_info_beer, container, false);
        setHasOptionsMenu(true);
        setUpPresenter();
        setUpScreenAndValues(view);
        setUpBeerInformation();
        return view;
    }

    private void setUpPresenter() {
        BeerModelData beerModelData = BeerModelData.getInstance(getContext());
        presenterListener = new DetailedInfoBeerPresenter(beerModelData, this);
    }

    private void setUpScreenAndValues(View view) {
        categoryBeerTextView = view.findViewById(R.id.category_text_beer);
        detailedInfoBeer = view.findViewById(R.id.description_text_beer);
        imageBeer = view.findViewById(R.id.image_beer);
        bottomNavigationHideListener.hideBottomNavMenu();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenterListener.detachView();
    }


    private void setUpBeerInformation() { // устанавливаем информацию о пиве
        Bundle bundle = getArguments();
        if (bundle != null) {
            BeerDetailedDescription beerDetailedDescription = bundle.getParcelable(DETAILED_INFO_BEER);
            if (beerDetailedDescription != null) {
                beerDetails = beerDetailedDescription;
                String iconUrl = beerDetailedDescription.getIconBigUrl();
                if (!TextUtils.isEmpty(iconUrl))
                    Glide.with(imageBeer)
                            .load(beerDetailedDescription.getIconBigUrl())
                            .into(imageBeer);
                categoryBeerTextView.setText(beerDetailedDescription.getCategoryBeer());
                detailedInfoBeer.setText(beerDetailedDescription.getDescription());
                AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
                if (appCompatActivity != null) {
                    ActionBar actionBar = appCompatActivity.getSupportActionBar();
                    if (actionBar != null) {
                        actionBar.setTitle(beerDetailedDescription.getNameBeer());
                    }
                }
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.detailed_beer_menu, menu);
        setUpActionBar();
        String uniqueIdBeer = beerDetails.getId();
        favoriteItem = menu.findItem(R.id.make_favorite_checker);
        presenterListener.setObserverChangeStatusBeer(uniqueIdBeer);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                moveBackListener.moveBack();
                return true;
            case R.id.make_favorite_checker:
                switchFavoriteState();
                return true;
        }
        return false;
    }

    private void setUpActionBar() { // настройка тулбара, (кнопка назад и тайтл)
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        if (appCompatActivity != null) {
            ActionBar actionBar = appCompatActivity.getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setDisplayShowTitleEnabled(true);
            }
        }
    }

    private void switchFavoriteState() {
        presenterListener.switchFavoriteButtonState(beerDetails);
    }

    @Override
    public void setFavoriteIcon(int icon) {
        favoriteItem.setIcon(icon);
    }
}

