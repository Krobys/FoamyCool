package com.akrivonos.beerdictionaryapplication.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Labels {

    @SerializedName("icon")
    @Expose
    private String icon;
    @SerializedName("medium")
    @Expose
    private String medium;
    @SerializedName("large")
    @Expose
    private String large;
    @SerializedName("contentAwareIcon")
    @Expose
    private String contentAwareIcon;
    @SerializedName("contentAwareMedium")
    @Expose
    private String contentAwareMedium;
    @SerializedName("contentAwareLarge")
    @Expose
    private String contentAwareLarge;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Labels withIcon(String icon) {
        this.icon = icon;
        return this;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public Labels withMedium(String medium) {
        this.medium = medium;
        return this;
    }

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public Labels withLarge(String large) {
        this.large = large;
        return this;
    }

    public String getContentAwareIcon() {
        return contentAwareIcon;
    }

    public void setContentAwareIcon(String contentAwareIcon) {
        this.contentAwareIcon = contentAwareIcon;
    }

    public Labels withContentAwareIcon(String contentAwareIcon) {
        this.contentAwareIcon = contentAwareIcon;
        return this;
    }

    public String getContentAwareMedium() {
        return contentAwareMedium;
    }

    public void setContentAwareMedium(String contentAwareMedium) {
        this.contentAwareMedium = contentAwareMedium;
    }

    public Labels withContentAwareMedium(String contentAwareMedium) {
        this.contentAwareMedium = contentAwareMedium;
        return this;
    }

    public String getContentAwareLarge() {
        return contentAwareLarge;
    }

    public void setContentAwareLarge(String contentAwareLarge) {
        this.contentAwareLarge = contentAwareLarge;
    }

    public Labels withContentAwareLarge(String contentAwareLarge) {
        this.contentAwareLarge = contentAwareLarge;
        return this;
    }

}