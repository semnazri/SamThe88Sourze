/*
 * Copyright (c) 2015 Konekthing.
 */

package com.konekthing.intravest.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by renjaya on 4/29/15.
 */
public class Profile implements Parcelable {
    private long id;
    private String email;
    private String salutation;
    private String firstname;
    private String lastname;
    private String company;
    private String position;
    private String phone;
    private String photo;

    public Profile() {
    }

    public Profile(Parcel source) {
        this.id = source.readLong();
        this.email = source.readString();
        this.salutation = source.readString();
        this.firstname = source.readString();
        this.lastname = source.readString();
        this.company = source.readString();
        this.position = source.readString();
        this.phone = source.readString();
        this.photo = source.readString();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(email);
        dest.writeString(salutation);
        dest.writeString(firstname);
        dest.writeString(lastname);
        dest.writeString(company);
        dest.writeString(position);
        dest.writeString(phone);
        dest.writeString(photo);
    }

    public static final Creator<Profile> CREATOR = new Creator<Profile>() {
        @Override
        public Profile createFromParcel(Parcel source) {
            return new Profile(source);
        }

        @Override
        public Profile[] newArray(int size) {
            return new Profile[size];
        }
    };
}
