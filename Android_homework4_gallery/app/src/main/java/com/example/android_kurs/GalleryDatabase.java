package com.example.android_kurs;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {GalleryItem.class}, version = 1)
public abstract class GalleryDatabase extends RoomDatabase {
    public abstract GalleryItemDao galleryImageDao();
}
