package com.example.android_kurs;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface GalleryItemDao {

    @Query("SELECT * FROM GalleryItem")
    List<GalleryItem> getAll();

    @Query("SELECT * FROM galleryItem WHERE id = :id")
    GalleryItem getById(long id);

    @Insert
    void insert(GalleryItem... image);

    @Delete
    void delete(GalleryItem image);
}
