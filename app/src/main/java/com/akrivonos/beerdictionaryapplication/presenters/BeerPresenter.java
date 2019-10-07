package com.akrivonos.beerdictionaryapplication.presenters;

import android.view.View;

import com.akrivonos.beerdictionaryapplication.BeerModel;
import com.akrivonos.beerdictionaryapplication.interfaces.mvp_listeners.presenter_listeners.BeerPresenterListener;
import com.akrivonos.beerdictionaryapplication.interfaces.mvp_listeners.view_control_listeners.BeerViewListener;
import com.akrivonos.beerdictionaryapplication.models.BeerDetailedDescription;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class BeerPresenter implements BeerPresenterListener{

    private final BeerModel beerModel;
    private Disposable observerDisposable;
    private final BeerViewListener viewPresenterListener;

    private final Observer<ArrayList<BeerDetailedDescription>> observerBeerListDownload = new Observer<ArrayList<BeerDetailedDescription>>() {//observer beer for retrofit
        @Override
        public void onSubscribe(Disposable d) {
            observerDisposable = d;
        }

        @Override
        public void onNext(ArrayList<BeerDetailedDescription> beerModels) {
            if (beerModels.size() != 0) {
                viewPresenterListener.showBeerList(beerModels);
            } else {
                viewPresenterListener.showToast("No Data");
            }
            viewPresenterListener.setVisibilityProgressBarLoading(View.GONE);
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
        }

        @Override
        public void onComplete() {
        }
    };

    @Override
    public void loadBeerList(String searchText){
        viewPresenterListener.setVisibilityProgressBarLoading(View.VISIBLE);
        beerModel.loadBeerListRetrofit(searchText);

    }

    public BeerPresenter(BeerModel beerModel, BeerViewListener beerViewListener) {
        this.beerModel = beerModel;
        beerModel.setBeerObserverRetrofit(observerBeerListDownload);
        this.viewPresenterListener = beerViewListener;
    }

    @Override
    public void detachView() {
        observerDisposable.dispose();
    }

}
