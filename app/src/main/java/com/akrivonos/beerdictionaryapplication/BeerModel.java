package com.akrivonos.beerdictionaryapplication;

import android.content.Context;

import com.akrivonos.beerdictionaryapplication.models.BeerDetailedDescription;
import com.akrivonos.beerdictionaryapplication.models.BreweryDetailedDescription;
import com.akrivonos.beerdictionaryapplication.retrofit.RetrofitSearchBeer;
import com.akrivonos.beerdictionaryapplication.room.RoomAppDatabase;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.akrivonos.beerdictionaryapplication.fragments.SearchBeerNameFragment.TYPE_BEER;

public class BeerModel {

    private final RoomAppDatabase roomAppDatabase;
    private final RetrofitSearchBeer retrofitSearchBeer;
    private static BeerModel beerModel;

    public static BeerModel getInstance(Context context){
        if(beerModel == null){
            beerModel = new BeerModel(context);
        }
        return beerModel;
    }

    private BeerModel(Context context){
        roomAppDatabase = RoomAppDatabase.getDatabase(context);
        retrofitSearchBeer = RetrofitSearchBeer.getInstance();
    }

    public void loadBeerListRetrofit(String searchText){
        retrofitSearchBeer
                .startDownloadBeerList(searchText, TYPE_BEER, 1);
    }

    public void loadBeersInBreweryRetrofit(String breweryId){
        retrofitSearchBeer
                .startDownloadBeersListInBrewery(breweryId);
    }

    public void setBeerObserverRetrofit(Observer<ArrayList<BeerDetailedDescription>> observer){
        retrofitSearchBeer.setObserverBeerNames(observer);
    }

    public void loadBreweryListRetrofit(LatLng latLng){
        retrofitSearchBeer
                .startDownloadBreweryList(latLng);
    }

    public void setBreweryObserverRetrofit(Observer<ArrayList<BreweryDetailedDescription>> observer){
        retrofitSearchBeer.setObserverBreweries(observer);
    }

    public void setBeerNotFavoriteDatabase(String beerId){
        roomAppDatabase.favoriteBeerDao().setBeerNotFavorite(beerId)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe();
    }

    public void setBeerFavoriteDatabase(BeerDetailedDescription beerDetailedDescription){
        roomAppDatabase.favoriteBeerDao().setBeerFavorite(beerDetailedDescription)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe();
    }

    public Disposable loadFavoriteBeerListDatabase(io.reactivex.functions.Consumer<List<BeerDetailedDescription>> consumer){
        return roomAppDatabase.favoriteBeerDao().getFavoritesBeer()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer);
    }

}
