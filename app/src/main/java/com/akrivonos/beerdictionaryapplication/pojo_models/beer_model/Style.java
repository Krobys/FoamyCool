package com.akrivonos.beerdictionaryapplication.pojo_models.beer_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Style {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("categoryId")
    @Expose
    private Integer categoryId;
    @SerializedName("category")
    @Expose
    private Category category;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("shortName")
    @Expose
    private String shortName;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("createDate")
    @Expose
    private String createDate;
    @SerializedName("updateDate")
    @Expose
    private String updateDate;
    @SerializedName("ibuMin")
    @Expose
    private String ibuMin;
    @SerializedName("ibuMax")
    @Expose
    private String ibuMax;
    @SerializedName("abvMin")
    @Expose
    private String abvMin;
    @SerializedName("abvMax")
    @Expose
    private String abvMax;
    @SerializedName("srmMin")
    @Expose
    private String srmMin;
    @SerializedName("srmMax")
    @Expose
    private String srmMax;
    @SerializedName("ogMin")
    @Expose
    private String ogMin;
    @SerializedName("fgMin")
    @Expose
    private String fgMin;
    @SerializedName("fgMax")
    @Expose
    private String fgMax;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Style withId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Style withCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Style withCategory(Category category) {
        this.category = category;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Style withName(String name) {
        this.name = name;
        return this;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Style withShortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Style withDescription(String description) {
        this.description = description;
        return this;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Style withCreateDate(String createDate) {
        this.createDate = createDate;
        return this;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public Style withUpdateDate(String updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public String getIbuMin() {
        return ibuMin;
    }

    public void setIbuMin(String ibuMin) {
        this.ibuMin = ibuMin;
    }

    public Style withIbuMin(String ibuMin) {
        this.ibuMin = ibuMin;
        return this;
    }

    public String getIbuMax() {
        return ibuMax;
    }

    public void setIbuMax(String ibuMax) {
        this.ibuMax = ibuMax;
    }

    public Style withIbuMax(String ibuMax) {
        this.ibuMax = ibuMax;
        return this;
    }

    public String getAbvMin() {
        return abvMin;
    }

    public void setAbvMin(String abvMin) {
        this.abvMin = abvMin;
    }

    public Style withAbvMin(String abvMin) {
        this.abvMin = abvMin;
        return this;
    }

    public String getAbvMax() {
        return abvMax;
    }

    public void setAbvMax(String abvMax) {
        this.abvMax = abvMax;
    }

    public Style withAbvMax(String abvMax) {
        this.abvMax = abvMax;
        return this;
    }

    public String getSrmMin() {
        return srmMin;
    }

    public void setSrmMin(String srmMin) {
        this.srmMin = srmMin;
    }

    public Style withSrmMin(String srmMin) {
        this.srmMin = srmMin;
        return this;
    }

    public String getSrmMax() {
        return srmMax;
    }

    public void setSrmMax(String srmMax) {
        this.srmMax = srmMax;
    }

    public Style withSrmMax(String srmMax) {
        this.srmMax = srmMax;
        return this;
    }

    public String getOgMin() {
        return ogMin;
    }

    public void setOgMin(String ogMin) {
        this.ogMin = ogMin;
    }

    public Style withOgMin(String ogMin) {
        this.ogMin = ogMin;
        return this;
    }

    public String getFgMin() {
        return fgMin;
    }

    public void setFgMin(String fgMin) {
        this.fgMin = fgMin;
    }

    public Style withFgMin(String fgMin) {
        this.fgMin = fgMin;
        return this;
    }

    public String getFgMax() {
        return fgMax;
    }

    public void setFgMax(String fgMax) {
        this.fgMax = fgMax;
    }

    public Style withFgMax(String fgMax) {
        this.fgMax = fgMax;
        return this;
    }

}
