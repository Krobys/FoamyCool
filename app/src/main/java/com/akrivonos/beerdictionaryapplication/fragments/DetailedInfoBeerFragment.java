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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.akrivonos.beerdictionaryapplication.R;
import com.akrivonos.beerdictionaryapplication.interfaces.BottomNavigationHideListener;
import com.akrivonos.beerdictionaryapplication.interfaces.MoveBackListener;
import com.akrivonos.beerdictionaryapplication.models.BeerDetailedDescription;
import com.akrivonos.beerdictionaryapplication.room.RoomAppDatabase;
import com.bumptech.glide.Glide;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.akrivonos.beerdictionaryapplication.MainActivity.DETAILED_INFO_BEER;

public class DetailedInfoBeerFragment extends Fragment {

    private TextView categoryBeerTextView;
    private TextView detailedInfoBeer;
    private ImageView imageBeer;
    private MoveBackListener moveBackListener;
    private BottomNavigationHideListener bottomNavigationHideListener;
    private RoomAppDatabase appDatabase;
    private BeerDetailedDescription beerDetails;
    private Disposable checkIsFavoriteBeerDisposable;
    private boolean isBeerFavorite;

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
            appDatabase = RoomAppDatabase.getDatabase(activity);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detailed_info_beer, container, false);
        setHasOptionsMenu(true);
        setUpScreenAndValues(view);
        setUpBeerInformation();
        return view;
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
        disposeAll();
    }

    private void disposeAll() {
        checkIsFavoriteBeerDisposable.dispose();
    }

    private void setUpBeerInformation() { // устанавливаем информацию о пиве
        Bundle bundle = getArguments();
        if (bundle != null) {
            BeerDetailedDescription beerDetailedDescription = bundle.getParcelable(DETAILED_INFO_BEER);
            if (beerDetailedDescription != null) {
                beerDetails = beerDetailedDescription;
                if (beerDetailedDescription.getIconBigUrl() != null)
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
        MenuItem favoriteItem = menu.findItem(R.id.make_favorite_checker);
        checkIsFavoriteBeerObserver(uniqueIdBeer, favoriteItem); // подписываемся на базу
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
        String uniqueBeerId = beerDetails.getId();
        if (isBeerFavorite) {
            appDatabase.favoriteBeerDao().setBeerNotFavorite(uniqueBeerId)//изменяем состояние в базе на не избранное
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe();
        } else {
            appDatabase.favoriteBeerDao().setBeerFavorite(beerDetails)//изменяем состояние в базе на избранное
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe();
        }
    }

    private void checkIsFavoriteBeerObserver(String uniqueId, MenuItem favoriteItem) { //подписываемся на базу данных и слушаем изменение состояния
        checkIsFavoriteBeerDisposable = appDatabase.favoriteBeerDao().checkIsBeerFavorite(uniqueId)
                .subscribeOn(Schedulers.io())
                .map(list -> {
                    isBeerFavorite = list.size() != 0;
                    return isBeerFavorite;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(isBeerFavorite -> favoriteItem.setIcon((isBeerFavorite)
                        ? R.drawable.ic_star_black_24dp
                        : R.drawable.ic_star_border_black_24dp));
    }
}

