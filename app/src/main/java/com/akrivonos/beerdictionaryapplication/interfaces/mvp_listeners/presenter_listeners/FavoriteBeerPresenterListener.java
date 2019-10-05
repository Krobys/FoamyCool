package com.akrivonos.beerdictionaryapplication.interfaces.mvp_listeners.presenter_listeners;

public interface FavoriteBeerPresenterListener {

    void detachView();

    void setBeerNotFavorite(String beerId);

    void loadFavoriteBeerList();
}
