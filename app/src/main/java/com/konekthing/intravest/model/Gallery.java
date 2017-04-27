/*
 * Copyright (c) 2015 Konekthing.
 */

package com.konekthing.intravest.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by renjaya on 5/11/15.
 */
public class Gallery implements Parcelable {

    private long id;
    private String image;
    private String title;

    public Gallery() {
    }

    public Gallery(Parcel source) {
        this.id = source.readLong();
        this.image = source.readString();
        this.title = source.readString();
    }

    public long getId() {
        return id;
    }

    public Gallery setId(long id) {
        this.id = id;
        return this;
    }

    public String getImage() {
        return image;
    }

    public Gallery setImage(String image) {
        this.image = image;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Gallery setTitle(String title) {
        this.title = title;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(image);
        dest.writeString(title);
    }

    public static final Creator<Gallery> CREATOR = new Creator<Gallery>() {
        @Override
        public Gallery createFromParcel(Parcel source) {
            return new Gallery(source);
        }

        @Override
        public Gallery[] newArray(int size) {
            return new Gallery[size];
        }
    };
}
