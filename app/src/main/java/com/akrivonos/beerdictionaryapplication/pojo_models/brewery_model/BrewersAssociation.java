package com.akrivonos.beerdictionaryapplication.pojo_models.brewery_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BrewersAssociation {

    @SerializedName("brewersAssocationId")
    @Expose
    private String brewersAssocationId;
    @SerializedName("isCertifiedCraftBrewer")
    @Expose
    private String isCertifiedCraftBrewer;

    public String getBrewersAssocationId() {
        return brewersAssocationId;
    }

    public void setBrewersAssocationId(String brewersAssocationId) {
        this.brewersAssocationId = brewersAssocationId;
    }

    public String getIsCertifiedCraftBrewer() {
        return isCertifiedCraftBrewer;
    }

    public void setIsCertifiedCraftBrewer(String isCertifiedCraftBrewer) {
        this.isCertifiedCraftBrewer = isCertifiedCraftBrewer;
    }

}