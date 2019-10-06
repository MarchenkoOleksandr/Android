package com.example.android_kurs;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity
class GalleryItem implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @SerializedName("imageUrl")
    private String imageUrl;

    GalleryItem(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    String getImageUrl() {
        return imageUrl;
    }
}
