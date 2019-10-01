package com.akrivonos.beerdictionaryapplication.retrofit;

import com.akrivonos.beerdictionaryapplication.models.BeerModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface ApiRetrofitInterface {

    @GET("search/")
    Call<BeerModel> searchBeerByName(@Query("key") String key, @Query("q") String requestName, @Query("type") String typeRequestOf, @Query("page") int page);

}
