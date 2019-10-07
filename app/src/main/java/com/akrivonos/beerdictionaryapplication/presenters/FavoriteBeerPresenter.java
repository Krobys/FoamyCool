package com.akrivonos.beerdictionaryapplication.presenters;

import android.view.View;

import com.akrivonos.beerdictionaryapplication.BeerModelData;
import com.akrivonos.beerdictionaryapplication.interfaces.mvp_listeners.presenter_listeners.FavoriteBeerPresenterListener;
import com.akrivonos.beerdictionaryapplication.interfaces.mvp_listeners.view_control_listeners.FavoriteBeerViewPresenterListener;
import com.akrivonos.beerdictionaryapplication.models.BeerDetailedDescription;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class FavoriteBeerPresenter implements FavoriteBeerPresenterListener {
    private Disposable disposableBeer;
    private final BeerModelData beerModelData;
    private final FavoriteBeerViewPresenterListener viewPresenterListener;

    private final Consumer<List<BeerDetailedDescription>> consumer = new Consumer<List<BeerDetailedDescription>>() {
        @Override
        public void accept(List<BeerDetailedDescription> beerDetailedDescriptions) {
            if (beerDetailedDescriptions.size() > 0) {
                viewPresenterListener.showBeerList(beerDetailedDescriptions);
                viewPresenterListener.setVisibilityEmptyMessage(View.GONE);
                viewPresenterListener.setVisibilityRecView(View.VISIBLE);
            } else {
                viewPresenterListener.setVisibilityEmptyMessage(View.VISIBLE);
                viewPresenterListener.setVisibilityRecView(View.GONE);
            }
        }
    };

    public FavoriteBeerPresenter(BeerModelData beerModelData, FavoriteBeerViewPresenterListener favoriteBeerViewPresenterListener) {
        this.beerModelData = beerModelData;
        this.viewPresenterListener = favoriteBeerViewPresenterListener;
    }

    @Override
    public void detachView() {
        disposableBeer.dispose();
    }

    @Override
    public void setBeerNotFavorite(String beerId) {
        beerModelData.setBeerNotFavoriteDatabase(beerId);
    }

    @Override
    public void loadFavoriteBeerList(){
        disposableBeer = beerModelData.loadFavoriteBeerListDatabase(consumer);
    }

}
