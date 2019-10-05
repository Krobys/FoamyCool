package com.akrivonos.beerdictionaryapplication.interfaces.mvp_listeners.presenter_listeners;

import com.google.android.gms.maps.model.LatLng;

public interface BreweriesPresenterListener {

    void loadBreweryList(LatLng latLng);

    void detachView();
}
