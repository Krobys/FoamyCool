package com.akrivonos.beerdictionaryapplication.retrofit;

import android.util.Log;

import androidx.annotation.NonNull;

import com.akrivonos.beerdictionaryapplication.models.BeerDetailedDescription;
import com.akrivonos.beerdictionaryapplication.models.BreweryDetailedDescription;
import com.akrivonos.beerdictionaryapplication.pojo_models.beer_model.BeerModel;
import com.akrivonos.beerdictionaryapplication.pojo_models.beer_model.Datum;
import com.akrivonos.beerdictionaryapplication.pojo_models.beer_model.Labels;
import com.akrivonos.beerdictionaryapplication.pojo_models.beers_in_brewery_model.BreweryBeersModel;
import com.akrivonos.beerdictionaryapplication.pojo_models.beers_in_brewery_model.Style;
import com.akrivonos.beerdictionaryapplication.pojo_models.brewery_model.BreweryModel;
import com.akrivonos.beerdictionaryapplication.pojo_models.brewery_model.Images;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.subjects.AsyncSubject;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.ReplaySubject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitSearchBeer {

    private Call lastProcess;
    private PublishSubject<ArrayList<BeerDetailedDescription>> beerPublishSubject;
    private PublishSubject<ArrayList<BreweryDetailedDescription>> breweryPublishSubject;
    private final static String SANDBOX_API_KEY = "14bac69989f93ce2755e0830d3a5c851";
    private final static String BASE_URL = "https://sandbox-api.brewerydb.com/v2/";
    private final static String UNIT_SEARCH_MI = "mi";
    private final static String RADIUS_SEARCH_MAX = "100";
    private static RetrofitSearchBeer retrofitSearchDownload;
    private final ApiRetrofitInterface apiService;

    private RetrofitSearchBeer() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiRetrofitInterface.class);
    }

    public static RetrofitSearchBeer getInstance() {
        if (retrofitSearchDownload == null) {
            retrofitSearchDownload = new RetrofitSearchBeer();
        }
        return retrofitSearchDownload;
    }

    public RetrofitSearchBeer setObserverBeerNames(io.reactivex.Observer<ArrayList<BeerDetailedDescription>> observer) {
        beerPublishSubject = PublishSubject.create();
        beerPublishSubject
                .subscribeOn(AndroidSchedulers.mainThread())//ПРОВЕРИТЬ
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        return retrofitSearchDownload;
    }

    public RetrofitSearchBeer setObserverBreweries(io.reactivex.Observer<ArrayList<BreweryDetailedDescription>> observer){
        breweryPublishSubject = PublishSubject.create();
        breweryPublishSubject
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        return retrofitSearchDownload;
    }

    public RetrofitSearchBeer startDownloadBeerList(String searchBeerName, String typeRequestName, int pageToLoad) {

        Call<BeerModel> beerModelCall = apiService.searchBeerByName(SANDBOX_API_KEY, searchBeerName, typeRequestName, pageToLoad);
        beerModelCall.enqueue(new Callback<BeerModel>() {
            @Override
            public void onResponse(@NonNull Call<BeerModel> call,@NonNull Response<BeerModel> response) {
                Log.d("test", "onResponse: "+response.toString());
                BeerModel beerModel = response.body();
                if(beerPublishSubject.hasObservers())
                    if(beerModel != null){
                        if (response.code() == 200){
                               beerPublishSubject.onNext(makeBeerListFromBeerModel(beerModel));
                        }
                        else{
                            beerPublishSubject.onNext(new ArrayList<>());
                        }
                    }
            }

            @Override
            public void onFailure(Call<BeerModel> call, Throwable t) {
                if(beerPublishSubject.hasObservers())
                    beerPublishSubject.onNext(new ArrayList<>());
            }

        });
        if (lastProcess != null) {
            if(!lastProcess.isExecuted())
            lastProcess.cancel();
        }
        lastProcess = beerModelCall;
        return retrofitSearchDownload;
    }

    public RetrofitSearchBeer startDownloadBreweryList(LatLng coordinatesForSearch) {

        Call<BreweryModel> breweryModelCall = apiService.searchBreweriesByCoordinate(SANDBOX_API_KEY,
                coordinatesForSearch.latitude,
                coordinatesForSearch.longitude,
                RADIUS_SEARCH_MAX,
                UNIT_SEARCH_MI);
        breweryModelCall.enqueue(new Callback<BreweryModel>() {
            @Override
            public void onResponse(@NonNull Call<BreweryModel> call,@NonNull Response<BreweryModel> response) {
                Log.d("test", "+" + response.toString());
                BreweryModel breweryModel = response.body();
                if(breweryModel != null){
                    if(breweryPublishSubject.hasObservers())
                        if (response.code() == 200){
                                breweryPublishSubject.onNext(makeBreweryListFromBreweryModel(breweryModel));
                            }
                        else{
                            breweryPublishSubject.onNext(new ArrayList<>());
                        }
                }
            }

            @Override
            public void onFailure(Call<BreweryModel> call, Throwable t) {
                if(breweryPublishSubject.hasObservers())
                    breweryPublishSubject.onNext(new ArrayList<>());

            }

        });
        return retrofitSearchDownload;
    }

    public RetrofitSearchBeer startDownloadBeersListInBrewery(String idBrewery){
        Call<BreweryBeersModel> breweryBeersModelCall = apiService.getBeersForBrewery(idBrewery, SANDBOX_API_KEY);
        breweryBeersModelCall.enqueue(new Callback<BreweryBeersModel>() {
            @Override
            public void onResponse(@NonNull Call<BreweryBeersModel> call,@NonNull Response<BreweryBeersModel> response) {
                Log.d("test", "+" + response.toString());
                BreweryBeersModel breweryBeersModel = response.body();
                if(breweryBeersModel != null){
                    if(beerPublishSubject.hasObservers())
                        if (response.code() == 200){
                                beerPublishSubject.onNext(makeBeerListFromBreweryBeersModel(breweryBeersModel));
                        }
                        else{
                            beerPublishSubject.onNext(new ArrayList<>());
                        }
                }
            }

            @Override
            public void onFailure(Call<BreweryBeersModel> call, Throwable t) {
                if(beerPublishSubject.hasObservers())
                    beerPublishSubject.onNext(new ArrayList<>());
            }

        });
        return retrofitSearchDownload;
    }

    private ArrayList<BeerDetailedDescription> makeBeerListFromBeerModel(BeerModel beerModel){
        BeerDetailedDescription beerDetailedDescription;
        ArrayList<BeerDetailedDescription> beerList = new ArrayList<>();
        List<Datum> dataList = beerModel.getData();
        if(dataList != null)
        for (Datum datum: dataList){
            beerDetailedDescription = new BeerDetailedDescription();
            beerDetailedDescription.setNameBeer(datum.getName());
            beerDetailedDescription.setCategoryBeer(datum.getStyle().getCategory().getName());
            beerDetailedDescription.setDescription(datum.getStyle().getDescription());
            Labels labels = datum.getLabels();
            if(labels != null){
                beerDetailedDescription.setIconBigUrl(datum.getLabels().getLarge());
                beerDetailedDescription.setIconSmallUrl(datum.getLabels().getIcon());
            }
            beerList.add(beerDetailedDescription);
        }
        return beerList;
    }

    private ArrayList<BeerDetailedDescription> makeBeerListFromBreweryBeersModel(BreweryBeersModel breweryBeersModel){
        BeerDetailedDescription beerDetailedDescription;
        ArrayList<BeerDetailedDescription> beerList = new ArrayList<>();
        List<com.akrivonos.beerdictionaryapplication.pojo_models.beers_in_brewery_model.Datum> dataList = breweryBeersModel.getData();
        if(dataList != null)
            for (com.akrivonos.beerdictionaryapplication.pojo_models.beers_in_brewery_model.Datum datum: dataList){
                beerDetailedDescription = new BeerDetailedDescription();
                beerDetailedDescription.setNameBeer(datum.getName());
                Style style = datum.getStyle();
                if(style!=null){
                    beerDetailedDescription.setCategoryBeer(style.getName());
                    beerDetailedDescription.setDescription(style.getDescription());
                    com.akrivonos.beerdictionaryapplication.pojo_models.beers_in_brewery_model.Labels labels = datum.getLabels();
                    if(labels != null){
                        beerDetailedDescription.setIconBigUrl(datum.getLabels().getLarge());
                        beerDetailedDescription.setIconSmallUrl(datum.getLabels().getIcon());
                    }
                    beerList.add(beerDetailedDescription);
                }
            }
        return beerList;
    }

    private ArrayList<BreweryDetailedDescription> makeBreweryListFromBreweryModel(BreweryModel breweryModel){
        BreweryDetailedDescription breweryDetailedDescription;
        ArrayList<BreweryDetailedDescription> breweryList = new ArrayList<>();
        List<com.akrivonos.beerdictionaryapplication.pojo_models.brewery_model.Datum> dataList = breweryModel.getData();
        if(dataList != null)
            for (com.akrivonos.beerdictionaryapplication.pojo_models.brewery_model.Datum datum: dataList){
                breweryDetailedDescription = new BreweryDetailedDescription();
                breweryDetailedDescription.setNameBrewery(datum.getBrewery().getName());
                breweryDetailedDescription.setDescriptionBrewery(datum.getBrewery().getDescription());
                breweryDetailedDescription.setIdBrewery(datum.getBrewery().getId());
                Images images = datum.getBrewery().getImages();
                if(images != null){
                    breweryDetailedDescription.setIconSmallUrl(images.getIcon());
                    breweryDetailedDescription.setIconBigUrl(images.getLarge());
                }
                breweryList.add(breweryDetailedDescription);
            }
        return breweryList;
    }
}
