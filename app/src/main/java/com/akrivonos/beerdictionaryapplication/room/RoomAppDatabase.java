package com.akrivonos.beerdictionaryapplication.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.akrivonos.beerdictionaryapplication.models.BeerDetailedDescription;

@Database(entities = {BeerDetailedDescription.class}, version = 2, exportSchema = false)
public abstract class RoomAppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "beer.database";
    private static RoomAppDatabase roomAppDatabase;

    public abstract FavoriteBeerDao favoriteBeerDao();

    public static RoomAppDatabase getDatabase(Context context) {
        if (roomAppDatabase == null) {
            roomAppDatabase = Room.databaseBuilder(context, RoomAppDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return roomAppDatabase;
    }
}
