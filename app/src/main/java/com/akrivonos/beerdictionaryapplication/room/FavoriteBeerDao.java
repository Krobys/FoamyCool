package com.akrivonos.beerdictionaryapplication.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.akrivonos.beerdictionaryapplication.models.BeerDetailedDescription;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface FavoriteBeerDao {

    @Query("SELECT * FROM BeerDetailedDescription")
    Flowable<List<BeerDetailedDescription>> getFavoritesBeer();

    @Insert
    void setBeerFavorite(BeerDetailedDescription beerDetailedDescription);

    @Query("DELETE FROM BeerDetailedDescription WHERE uniqueId = :uniqueBeerId")
    void setBeerNotFavorite(String uniqueBeerId);

    @Query("SELECT * FROM BeerDetailedDescription WHERE uniqueId = (:uniqueBeerId)")
    Flowable<List<BeerDetailedDescription>> checkIsBeerFavorite(String uniqueBeerId);

}
