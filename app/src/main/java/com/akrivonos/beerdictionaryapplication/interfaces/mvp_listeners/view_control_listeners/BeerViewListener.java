package com.akrivonos.beerdictionaryapplication.interfaces.mvp_listeners.view_control_listeners;

import com.akrivonos.beerdictionaryapplication.models.BeerDetailedDescription;

import java.util.List;

public interface BeerViewListener {

    void showBeerList(List<BeerDetailedDescription> beerDetailedDescriptions);

    void showToast(String message);

    void setVisibilityProgressBarLoading(int visibility);

    void showSnackBar(String message);
}
