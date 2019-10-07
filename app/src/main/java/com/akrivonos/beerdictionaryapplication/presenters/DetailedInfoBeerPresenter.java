package com.akrivonos.beerdictionaryapplication.presenters;

import com.akrivonos.beerdictionaryapplication.BeerModelData;
import com.akrivonos.beerdictionaryapplication.R;
import com.akrivonos.beerdictionaryapplication.interfaces.mvp_listeners.presenter_listeners.DetailedBeerPresenterListener;
import com.akrivonos.beerdictionaryapplication.interfaces.mvp_listeners.view_control_listeners.DetailedBeerViewListener;
import com.akrivonos.beerdictionaryapplication.models.BeerDetailedDescription;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class DetailedInfoBeerPresenter implements DetailedBeerPresenterListener {
    private final BeerModelData beerModelData;
    private final DetailedBeerViewListener viewPresenterListener;
    private Disposable disposableBeer;
    private boolean isFavoriteBeer;

    private final Consumer<List<BeerDetailedDescription>> observerBeer = new Consumer<List<BeerDetailedDescription>>() {
        @Override
        public void accept(List<BeerDetailedDescription> beerDetailedDescriptions) {
            isFavoriteBeer = beerDetailedDescriptions.size() > 0;
            viewPresenterListener.setFavoriteIcon((isFavoriteBeer)
                    ? R.drawable.ic_star_black_24dp
                    : R.drawable.ic_star_border_black_24dp);
        }
    };

    public DetailedInfoBeerPresenter(BeerModelData beerModelData, DetailedBeerViewListener detailedBeerViewListener) {
        this.beerModelData = beerModelData;
        this.viewPresenterListener = detailedBeerViewListener;
    }

    @Override
    public void setObserverChangeStatusBeer(String uniqueIdBeer) {
        disposableBeer = beerModelData.setListenerChangeFavoriteStatusBeer(observerBeer, uniqueIdBeer);
    }

    @Override
    public void detachView() {
        disposableBeer.dispose();
    }

    @Override
    public void switchFavoriteButtonState(BeerDetailedDescription beerDetailedDescription) {
        if (isFavoriteBeer) {
            String beerUniqueId = beerDetailedDescription.getId();
            setBeerNotFavorite(beerUniqueId);
        } else {
            setBeerFavorite(beerDetailedDescription);
        }
    }

    private void setBeerFavorite(BeerDetailedDescription beerDetails) {
        beerModelData.setBeerFavoriteDatabase(beerDetails);
    }

    private void setBeerNotFavorite(String uniqueBeerId) {
        beerModelData.setBeerNotFavoriteDatabase(uniqueBeerId);
    }

}
