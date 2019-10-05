package com.akrivonos.beerdictionaryapplication.interfaces.mvp_listeners.view_control_listeners;

import com.akrivonos.beerdictionaryapplication.models.BeerDetailedDescription;

import java.util.List;

public interface DetailedBreweryViewListener {

    void setBeerinBreweryList(List<BeerDetailedDescription> beerinBreweryList);

    void setVisibilityEmptyMessage(int visibility);

    void setVisibilityProgressBar(int visibility);
}
