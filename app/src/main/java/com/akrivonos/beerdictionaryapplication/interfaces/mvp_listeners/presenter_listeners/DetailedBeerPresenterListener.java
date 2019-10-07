package com.akrivonos.beerdictionaryapplication.interfaces.mvp_listeners.presenter_listeners;

import com.akrivonos.beerdictionaryapplication.models.BeerDetailedDescription;

public interface DetailedBeerPresenterListener {

    void detachView();

    void switchFavoriteButtonState(BeerDetailedDescription beerDetailedDescription);

    void setObserverChangeStatusBeer(String beerId);
}
