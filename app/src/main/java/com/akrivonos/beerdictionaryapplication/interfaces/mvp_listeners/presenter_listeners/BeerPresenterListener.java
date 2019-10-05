package com.akrivonos.beerdictionaryapplication.interfaces.mvp_listeners.presenter_listeners;

public interface BeerPresenterListener {

    void detachView();

    void loadBeerList(String searchRequest);

}
