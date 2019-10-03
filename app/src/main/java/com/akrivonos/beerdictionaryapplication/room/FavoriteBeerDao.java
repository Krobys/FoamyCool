package com.akrivonos.beerdictionaryapplication.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.akrivonos.beerdictionaryapplication.models.BeerDetailedDescription;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

@Dao
public interface FavoriteBeerDao {

    @Query("SELECT * FROM BeerDetailedDescription ORDER BY idInc DESC")
    Flowable<List<BeerDetailedDescription>> getFavoritesBeer();

    @Insert
    Completable setBeerFavorite(BeerDetailedDescription beerDetailedDescription);

    @Query("DELETE FROM BeerDetailedDescription WHERE uniqueId = :uniqueBeerId")
    Completable setBeerNotFavorite(String uniqueBeerId);

    @Query("SELECT * FROM BeerDetailedDescription WHERE uniqueId = (:uniqueBeerId)")
    Flowable<List<BeerDetailedDescription>> checkIsBeerFavorite(String uniqueBeerId);

}
