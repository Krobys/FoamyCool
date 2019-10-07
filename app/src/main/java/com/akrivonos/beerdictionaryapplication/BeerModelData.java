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

public class BeerModelData {

    private final RoomAppDatabase roomAppDatabase;
    private final RetrofitSearchBeer retrofitSearchBeer;
    private static BeerModelData beerModelData;

    private BeerModelData(Context context) {
        roomAppDatabase = RoomAppDatabase.getDatabase(context);
        retrofitSearchBeer = RetrofitSearchBeer.getInstance();
    }

    public static BeerModelData getInstance(Context context) {
        if (beerModelData == null) {
            beerModelData = new BeerModelData(context);
        }
        return beerModelData;
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

    public Disposable setListenerChangeFavoriteStatusBeer(io.reactivex.functions.Consumer<List<BeerDetailedDescription>> consumer, String uniqueIdBeer) {
        return roomAppDatabase.favoriteBeerDao().checkIsBeerFavorite(uniqueIdBeer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer);
    }
    public Disposable loadFavoriteBeerListDatabase(io.reactivex.functions.Consumer<List<BeerDetailedDescription>> consumer){
        return roomAppDatabase.favoriteBeerDao().getFavoritesBeer()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer);
    }

}
