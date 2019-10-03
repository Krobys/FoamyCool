package com.akrivonos.beerdictionaryapplication.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.akrivonos.beerdictionaryapplication.utils.UrlUtils;

public class BreweryDetailedDescription implements Parcelable {

    private String iconSmallUrl;
    private String iconBigUrl;
    private String idBrewery;
    private String nameBrewery;
    private String descriptionBrewery;

    public BreweryDetailedDescription() {

    }

    private BreweryDetailedDescription(Parcel in) {
        iconSmallUrl = in.readString();
        iconBigUrl = in.readString();
        idBrewery = in.readString();
        nameBrewery = in.readString();
        descriptionBrewery = in.readString();
    }

    public static final Creator<BreweryDetailedDescription> CREATOR = new Creator<BreweryDetailedDescription>() {
        @Override
        public BreweryDetailedDescription createFromParcel(Parcel in) {
            return new BreweryDetailedDescription(in);
        }

        @Override
        public BreweryDetailedDescription[] newArray(int size) {
            return new BreweryDetailedDescription[size];
        }
    };

    public String getIconSmallUrl() {
        return iconSmallUrl;
    }

    public void setIconSmallUrl(String iconSmallUrl) {
        this.iconSmallUrl = UrlUtils.makeUrlIconFix(iconSmallUrl);
    }

    public String getIconBigUrl() {
        return iconBigUrl;
    }

    public void setIconBigUrl(String iconBigUrl) {
        this.iconBigUrl = UrlUtils.makeUrlIconFix(iconBigUrl);
    }

    public String getIdBrewery() {
        return idBrewery;
    }

    public void setIdBrewery(String idBrewery) {
        this.idBrewery = idBrewery;
    }

    public String getNameBrewery() {
        return nameBrewery;
    }

    public void setNameBrewery(String nameBrewery) {
        this.nameBrewery = nameBrewery;
    }

    public String getDescriptionBrewery() {
        return descriptionBrewery;
    }

    public void setDescriptionBrewery(String descriptionBrewery) {
        this.descriptionBrewery = descriptionBrewery;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(iconSmallUrl);
        parcel.writeString(iconBigUrl);
        parcel.writeString(idBrewery);
        parcel.writeString(nameBrewery);
        parcel.writeString(descriptionBrewery);
    }
}
