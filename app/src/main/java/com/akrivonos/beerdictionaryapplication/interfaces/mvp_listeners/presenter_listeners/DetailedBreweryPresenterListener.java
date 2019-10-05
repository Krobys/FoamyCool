package com.akrivonos.beerdictionaryapplication.interfaces.mvp_listeners.presenter_listeners;

public interface DetailedBreweryPresenterListener {

    void detachView();

    void loadBeerInBreweryList(String idBrewery);
}
