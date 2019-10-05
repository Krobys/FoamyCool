package com.akrivonos.beerdictionaryapplication.interfaces.mvp_listeners.view_control_listeners;

import com.akrivonos.beerdictionaryapplication.models.BeerDetailedDescription;

import java.util.List;

public interface FavoriteBeerViewPresenterListener {

    void showBeerList(List<BeerDetailedDescription> beerDetailedDescriptions);

    void setVisibilityEmptyMessage(int visibility);

    void setVisibilityRecView(int visibility);
}
