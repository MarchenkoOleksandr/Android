package com.example.android_kurs;

import android.app.Application;
import android.arch.persistence.room.Room;

public class App extends Application {

    public static App instance;

    private GalleryDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, GalleryDatabase.class, "database")
                .build();
    }

    public static App getInstance() {
        return instance;
    }

    public GalleryDatabase getDatabase() {
        return database;
    }
}
