/*
 * Copyright (c) 2015 Konekthing.
 */

package com.konekthing.intravest.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by renjaya on 5/11/15.
 */
public class Feed implements Parcelable {

    private long id;
    private long userId;
    private String salutation;
    private String firstname;
    private String lastname;
    private String company;
    private String content;
    private String time;

    public Feed() {
    }

    public Feed(Parcel source) {
        this.id = source.readLong();
        this.userId = source.readLong();
        this.salutation = source.readString();
        this.firstname = source.readString();
        this.lastname = source.readString();
        this.company = source.readString();
        this.content = source.readString();
        this.time = source.readString();
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

    public String getSalutation() {
        return salutation;
    }

    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeLong(userId);
        dest.writeString(salutation);
        dest.writeString(firstname);
        dest.writeString(lastname);
        dest.writeString(company);
        dest.writeString(content);
        dest.writeString(time);
    }

    public static final Creator<Feed> CREATOR = new Creator<Feed>() {
        @Override
        public Feed createFromParcel(Parcel source) {
            return new Feed(source);
        }

        @Override
        public Feed[] newArray(int size) {
            return new Feed[size];
        }
    };
}
