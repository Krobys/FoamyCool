package com.akrivonos.beerdictionaryapplication.retrofit;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.akrivonos.beerdictionaryapplication.models.BeerDetailedDescription;
import com.akrivonos.beerdictionaryapplication.models.BeerModel;
import com.akrivonos.beerdictionaryapplication.models.Datum;
import com.akrivonos.beerdictionaryapplication.models.Labels;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitSearchBeer {

    private Call<BeerModel> lastProcess;
    private String searchText;
    private PublishSubject<ArrayList<BeerDetailedDescription>> beerPublishSubject;
    private final static String SANDBOX_API_KEY = "14bac69989f93ce2755e0830d3a5c851";
    private final static String BASE_URL = "https://sandbox-api.brewerydb.com/v2/";
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
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        return retrofitSearchDownload;
    }

    public RetrofitSearchBeer startDownloadPictures(String searchBeerName, String typeRequestName, int pageToLoad) {

        Call<BeerModel> beerModelCall = apiService.searchBeerByName(SANDBOX_API_KEY, searchBeerName, typeRequestName, pageToLoad);
        beerModelCall.enqueue(new Callback<BeerModel>() {
            @Override
            public void onResponse(@NonNull Call<BeerModel> call,@NonNull Response<BeerModel> response) {

                BeerModel beerModel = response.body();
                if(beerModel != null){
                    if (response.code() == 200){
                       if(beerPublishSubject.hasObservers()){
                           Log.d("test", "onResponse: ");
                           beerPublishSubject.onNext(makeBeerListFromBeerModel(beerModel));
                       }
                    }
                    else{
                        beerPublishSubject.onNext(new ArrayList<>());
                    }
                }
            }

            @Override
            public void onFailure(Call<BeerModel> call, Throwable t) {

            }

        });
        lastProcess = beerModelCall;
        return retrofitSearchDownload;
    }

    public RetrofitSearchBeer cancelLastDownloadingProcess(){
        if(lastProcess != null)
        if(!lastProcess.isExecuted()){
            lastProcess.cancel();
            lastProcess = null;
        }
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
            Log.d("test", "description: "+datum.getStyle().getDescription());
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
}
