package com.akrivonos.beerdictionaryapplication.presenters;

import android.view.View;

import com.akrivonos.beerdictionaryapplication.BeerModel;
import com.akrivonos.beerdictionaryapplication.interfaces.mvp_listeners.presenter_listeners.DetailedBreweryPresenterListener;
import com.akrivonos.beerdictionaryapplication.interfaces.mvp_listeners.view_control_listeners.DetailedBreweryViewListener;
import com.akrivonos.beerdictionaryapplication.models.BeerDetailedDescription;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class DetailedInfoBreweryPresenter implements DetailedBreweryPresenterListener {
    private Disposable disposableBeer;
    private final BeerModel beerModel;
    private final DetailedBreweryViewListener viewPresenterListener;

    private final Observer<ArrayList<BeerDetailedDescription>> observerBeer = new Observer<ArrayList<BeerDetailedDescription>>() { // observer for retrofit
        @Override
        public void onSubscribe(Disposable d) {
            disposableBeer = d;
        }

        @Override
        public void onNext(ArrayList<BeerDetailedDescription> beerModels) {
            if (beerModels.size() != 0) {
                viewPresenterListener.setBeerinBreweryList(beerModels);
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

    public DetailedInfoBreweryPresenter(BeerModel beerModel, DetailedBreweryViewListener detailedBreweryViewListener) {
        this.beerModel = beerModel;
        beerModel.setBeerObserverRetrofit(observerBeer);
        this.viewPresenterListener = detailedBreweryViewListener;
    }

    @Override
    public void detachView() {
        disposableBeer.dispose();
    }

    @Override
    public void loadBeerInBreweryList(String idBrewery) {
        beerModel.loadBeersInBreweryRetrofit(idBrewery);
    }

}
