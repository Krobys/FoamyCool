package com.akrivonos.beerdictionaryapplication.interfaces.mvp_listeners.view_control_listeners;

import com.akrivonos.beerdictionaryapplication.models.BreweryDetailedDescription;

import java.util.List;

public interface BreweryViewListener {

    void showBreweryList(List<BreweryDetailedDescription> breweryList);

    void setVisibilityEmptyMessage(int visibility);

    void setVisibilityProgressBar(int visibility);
}
