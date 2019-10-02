package com.akrivonos.beerdictionaryapplication.pojo_models.beer_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("nameDisplay")
    @Expose
    private String nameDisplay;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("abv")
    @Expose
    private String abv;
    @SerializedName("styleId")
    @Expose
    private Integer styleId;
    @SerializedName("isOrganic")
    @Expose
    private String isOrganic;
    @SerializedName("isRetired")
    @Expose
    private String isRetired;
    @SerializedName("labels")
    @Expose
    private Labels labels;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("statusDisplay")
    @Expose
    private String statusDisplay;
    @SerializedName("createDate")
    @Expose
    private String createDate;
    @SerializedName("updateDate")
    @Expose
    private String updateDate;
    @SerializedName("style")
    @Expose
    private Style style;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("glasswareId")
    @Expose
    private Integer glasswareId;
    @SerializedName("availableId")
    @Expose
    private Integer availableId;
    @SerializedName("glass")
    @Expose
    private Glass glass;
    @SerializedName("available")
    @Expose
    private Available available;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Datum withId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Datum withName(String name) {
        this.name = name;
        return this;
    }

    public String getNameDisplay() {
        return nameDisplay;
    }

    public void setNameDisplay(String nameDisplay) {
        this.nameDisplay = nameDisplay;
    }

    public Datum withNameDisplay(String nameDisplay) {
        this.nameDisplay = nameDisplay;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Datum withDescription(String description) {
        this.description = description;
        return this;
    }

    public String getAbv() {
        return abv;
    }

    public void setAbv(String abv) {
        this.abv = abv;
    }

    public Datum withAbv(String abv) {
        this.abv = abv;
        return this;
    }

    public Integer getStyleId() {
        return styleId;
    }

    public void setStyleId(Integer styleId) {
        this.styleId = styleId;
    }

    public Datum withStyleId(Integer styleId) {
        this.styleId = styleId;
        return this;
    }

    public String getIsOrganic() {
        return isOrganic;
    }

    public void setIsOrganic(String isOrganic) {
        this.isOrganic = isOrganic;
    }

    public Datum withIsOrganic(String isOrganic) {
        this.isOrganic = isOrganic;
        return this;
    }

    public String getIsRetired() {
        return isRetired;
    }

    public void setIsRetired(String isRetired) {
        this.isRetired = isRetired;
    }

    public Datum withIsRetired(String isRetired) {
        this.isRetired = isRetired;
        return this;
    }

    public Labels getLabels() {
        return labels;
    }

    public void setLabels(Labels labels) {
        this.labels = labels;
    }

    public Datum withLabels(Labels labels) {
        this.labels = labels;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Datum withStatus(String status) {
        this.status = status;
        return this;
    }

    public String getStatusDisplay() {
        return statusDisplay;
    }

    public void setStatusDisplay(String statusDisplay) {
        this.statusDisplay = statusDisplay;
    }

    public Datum withStatusDisplay(String statusDisplay) {
        this.statusDisplay = statusDisplay;
        return this;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Datum withCreateDate(String createDate) {
        this.createDate = createDate;
        return this;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public Datum withUpdateDate(String updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public Datum withStyle(Style style) {
        this.style = style;
        return this;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Datum withType(String type) {
        this.type = type;
        return this;
    }

    public Integer getGlasswareId() {
        return glasswareId;
    }

    public void setGlasswareId(Integer glasswareId) {
        this.glasswareId = glasswareId;
    }

    public Datum withGlasswareId(Integer glasswareId) {
        this.glasswareId = glasswareId;
        return this;
    }

    public Integer getAvailableId() {
        return availableId;
    }

    public void setAvailableId(Integer availableId) {
        this.availableId = availableId;
    }

    public Datum withAvailableId(Integer availableId) {
        this.availableId = availableId;
        return this;
    }

    public Glass getGlass() {
        return glass;
    }

    public void setGlass(Glass glass) {
        this.glass = glass;
    }

    public Datum withGlass(Glass glass) {
        this.glass = glass;
        return this;
    }

    public Available getAvailable() {
        return available;
    }

    public void setAvailable(Available available) {
        this.available = available;
    }

    public Datum withAvailable(Available available) {
        this.available = available;
        return this;
    }

}
