/*
 * Copyright (c) 2015 Konekthing.
 */

package com.konekthing.intravest.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by renjaya on 4/7/15.
 */
public class Opportunity implements Parcelable {
    private long id;
    private long userId;
    private String projectName;
    private String projectDescription;
    private String projectValue;
    private String currency;
    private String owner;
    private String status;
    private String dateOpen;
    private String dateClose;
    private String regional;
    private String briefingDocument;

    public Opportunity() {
    }

    public Opportunity(Parcel source) {
        this.id = source.readLong();
        this.userId = source.readLong();
        this.projectName = source.readString();
        this.projectDescription = source.readString();
        this.projectValue = source.readString();
        this.owner = source.readString();
        this.status = source.readString();
        this.dateOpen = source.readString();
        this.dateClose = source.readString();
        this.currency = source.readString();
        this.regional = source.readString();
        this.briefingDocument = source.readString();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public String getProjectValue() {
        return projectValue;
    }

    public void setProjectValue(String projectValue) {
        this.projectValue = projectValue;
    }

    public String getDateOpen() {
        return dateOpen;
    }

    public void setDateOpen(String dateOpen) {
        this.dateOpen = dateOpen;
    }

    public String getDateClose() {
        return dateClose;
    }

    public void setDateClose(String dateClose) {
        this.dateClose = dateClose;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getRegional() {
        return regional;
    }

    public void setRegional(String regional) {
        this.regional = regional;
    }

    public String getBriefingDocument() {
        return briefingDocument;
    }

    public void setBriefingDocument(String briefingDocument) {
        this.briefingDocument = briefingDocument;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeLong(userId);
        dest.writeString(projectName);
        dest.writeString(projectDescription);
        dest.writeString(projectValue);
        dest.writeString(owner);
        dest.writeString(status);
        dest.writeString(dateOpen);
        dest.writeString(dateClose);
        dest.writeString(currency);
        dest.writeString(regional);
        dest.writeString(briefingDocument);
    }

    public static final Creator<Opportunity> CREATOR = new Creator<Opportunity>() {
        @Override
        public Opportunity createFromParcel(Parcel source) {
            return new Opportunity(source);
        }

        @Override
        public Opportunity[] newArray(int size) {
            return new Opportunity[size];
        }
    };
}
