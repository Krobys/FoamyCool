package com.akrivonos.beerdictionaryapplication.retrofit;

import com.akrivonos.beerdictionaryapplication.pojo_models.beer_model.BeerModel;
import com.akrivonos.beerdictionaryapplication.pojo_models.beers_in_brewery_model.BreweryBeersModel;
import com.akrivonos.beerdictionaryapplication.pojo_models.brewery_model.BreweryModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

interface ApiRetrofitInterface {

    @GET("search/")
    Call<BeerModel> searchBeerByName(@Query("key") String key, @Query("q") String requestName, @Query("type") String typeRequestOf, @Query("page") int page);

    @GET("search/geo/point/")
    Call<BreweryModel> searchBreweriesByCoordinate(@Query("key") String key, @Query("lat") Double lat, @Query("lng") Double lng, @Query("radius") String radiusSearch, @Query("unit") String unitSearch);

    @GET("brewery/{idBrewery}/beers/")
    Call<BreweryBeersModel> getBeersForBrewery(@Path("idBrewery") String idBrewery, @Query("key") String key);
}
