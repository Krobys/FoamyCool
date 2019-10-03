package com.akrivonos.beerdictionaryapplication.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
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
    private BeerDetailedDescription beerDeatils;
    private Disposable checkIsFavoriteBeerDisposable;
    private boolean isBeerFavorite;

    public DetailedInfoBeerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Activity activity = getActivity();
        if(activity != null){
            moveBackListener = (MoveBackListener) getActivity();
            bottomNavigationHideListener = (BottomNavigationHideListener) getActivity();
            appDatabase = RoomAppDatabase.getDatabase(activity);
        }

        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        bottomNavigationHideListener.hideBottomNavMenu();
        super.onResume();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detailed_info_beer, container, false);
        Log.d("test", "setHasOptionsMenu: ");
        setHasOptionsMenu(true);
        categoryBeerTextView = view.findViewById(R.id.category_text_beer);
        detailedInfoBeer = view.findViewById(R.id.description_text_beer);
        imageBeer = view.findViewById(R.id.image_beer);
        setUpBeerInformation();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        disposeAll();
    }

    private void disposeAll(){
        checkIsFavoriteBeerDisposable.dispose();
    }

    private void setUpBeerInformation(){
        Bundle bundle = getArguments();
        if(bundle != null){
            BeerDetailedDescription beerDetailedDescription = bundle.getParcelable(DETAILED_INFO_BEER);
            if(beerDetailedDescription != null){
                beerDeatils = beerDetailedDescription;
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
        Log.d("test", "onCreateOptionsMenu: ");
        inflater.inflate(R.menu.detailed_beer_menu, menu);
        setUpActionBar();
        String uniqueIdBeer = beerDeatils.getId();
        Log.d("test", "onCreateOptionsMenu: uniq id = "+uniqueIdBeer);
        MenuItem favoriteItem = menu.findItem(R.id.make_favorite_checker);
        checkIsFavoriteBeerSetter(uniqueIdBeer, favoriteItem);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                moveBackListener.moveBack();
                break;
            case R.id.make_favorite_checker:
                switchFavoriteState();
                break;
        }
        return false;
    }

    private void setUpActionBar(){
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        if(appCompatActivity != null){
            ActionBar actionBar = appCompatActivity.getSupportActionBar();
            if(actionBar != null){
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setDisplayShowTitleEnabled(true);
            }
        }
    }

    private void switchFavoriteState(){
        String uniqueBeerId = beerDeatils.getId();
        if(isBeerFavorite){
            appDatabase.favoriteBeerDao().setBeerNotFavorite(uniqueBeerId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe();
        }else{
            appDatabase.favoriteBeerDao().setBeerFavorite(beerDeatils)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe();
        }
    }

    private void checkIsFavoriteBeerSetter(String uniqueId, MenuItem favoriteItem){
        checkIsFavoriteBeerDisposable = appDatabase.favoriteBeerDao().checkIsBeerFavorite(uniqueId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> {
                            Log.d("test", "checkIsFavoriteBeerSetter: subscribe");
                    isBeerFavorite = (o.size() != 0);
                    favoriteItem.setIcon((isBeerFavorite) ?  R.drawable.ic_star_black_24dp : R.drawable.ic_star_border_black_24dp );
                });
    }
}

