package com.akrivonos.beerdictionaryapplication.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import static com.akrivonos.beerdictionaryapplication.utils.UrlUtils.makeUrlIconFix;

@Entity
public class BeerDetailedDescription implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private long idInc;
    @ColumnInfo(name = "uniqueId")
    private String id;
    @ColumnInfo(name = "iconSmall")
    private String iconSmallUrl;
    @ColumnInfo(name = "iconBig")
    private String iconBigUrl;
    @ColumnInfo(name = "name")
    private String nameBeer;
    @ColumnInfo(name = "category")
    private String categoryBeer;
    @ColumnInfo(name = "description")
    private String description;

    public BeerDetailedDescription(){

    }

    private BeerDetailedDescription(Parcel in) {
        iconSmallUrl = in.readString();
        iconBigUrl = in.readString();
        nameBeer = in.readString();
        categoryBeer = in.readString();
        description = in.readString();
    }

    public static final Creator<BeerDetailedDescription> CREATOR = new Creator<BeerDetailedDescription>() {
        @Override
        public BeerDetailedDescription createFromParcel(Parcel in) {
            return new BeerDetailedDescription(in);
        }

        @Override
        public BeerDetailedDescription[] newArray(int size) {
            return new BeerDetailedDescription[size];
        }
    };

    public String getIconSmallUrl() {
        return iconSmallUrl;
    }

    public void setIconSmallUrl(String iconSmallUrl) {
        this.iconSmallUrl = makeUrlIconFix(iconSmallUrl);
    }

    public String getIconBigUrl() {
        return iconBigUrl;
    }

    public void setIconBigUrl(String iconBigUrl) {
        this.iconBigUrl = makeUrlIconFix(iconBigUrl);
    }

    public String getNameBeer() {
        return nameBeer;
    }

    public void setNameBeer(String nameBeer) {
        this.nameBeer = nameBeer;
    }

    public String getCategoryBeer() {
        return categoryBeer;
    }

    public void setCategoryBeer(String categoryBeer) {
        this.categoryBeer = categoryBeer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getIdInc() {
        return idInc;
    }

    public void setIdInc(long idInc) {
        this.idInc = idInc;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(iconSmallUrl);
        parcel.writeString(iconBigUrl);
        parcel.writeString(nameBeer);
        parcel.writeString(categoryBeer);
        parcel.writeString(description);
    }
}
