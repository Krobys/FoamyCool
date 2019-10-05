package com.akrivonos.beerdictionaryapplication.presenters;

import android.view.View;

import com.akrivonos.beerdictionaryapplication.BeerModel;
import com.akrivonos.beerdictionaryapplication.interfaces.mvp_listeners.presenter_listeners.BreweriesPresenterListener;
import com.akrivonos.beerdictionaryapplication.interfaces.mvp_listeners.view_control_listeners.BreweryViewListener;
import com.akrivonos.beerdictionaryapplication.models.BreweryDetailedDescription;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class BreweryPresenter implements BreweriesPresenterListener {
    private final BeerModel beerModel;
    private Disposable observerDisposable;
    private BreweryViewListener viewPresenterListener;

    private final Observer<ArrayList<BreweryDetailedDescription>> observerBrewery = new Observer<ArrayList<BreweryDetailedDescription>>() {
        @Override
        public void onSubscribe(Disposable d) {
            observerDisposable = d;
        }

        @Override
        public void onNext(ArrayList<BreweryDetailedDescription> breweryModels) {
            if (breweryModels.size() != 0) {
               viewPresenterListener.showBreweryList(breweryModels);
                viewPresenterListener.setVisibilityEmptyMessage(View.GONE);
            } else {
                viewPresenterListener.setVisibilityEmptyMessage(View.VISIBLE);
            }
            viewPresenterListener.setVisibilityProgressBar(View.GONE);
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
        }

        @Override
        public void onComplete() {

        }
    };

    public BreweryPresenter(BeerModel beerModel, BreweryViewListener breweryViewListener) {
        this.beerModel = beerModel;
        beerModel.setBreweryObserverRetrofit(observerBrewery);
        this.viewPresenterListener = breweryViewListener;
    }

    @Override
    public void loadBreweryList(LatLng latLng) {
        viewPresenterListener.setVisibilityProgressBar(View.VISIBLE);
        beerModel.loadBreweryListRetrofit(latLng);
    }

    @Override
    public void detachView() {
        observerDisposable.dispose();
    }
}
