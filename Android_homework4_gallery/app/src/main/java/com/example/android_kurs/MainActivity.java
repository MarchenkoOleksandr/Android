package com.example.android_kurs;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<GalleryItem> listData;
    UnSplashApiService apiInterface;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        if (getSavedMode().equals("grid")) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }

        adapter = new MyAdapter(MainActivity.this);
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateImages();
            }
        });
        recyclerView.setAdapter(adapter);
        apiInterface = RetrofitInstance.getRetrofitInstance().create(UnSplashApiService.class);
        generateImages();



    }

    private String getSavedMode() {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        return preferences.getString("myMode", "");
    }


    private void generateImages() {
        Call<ArrayList<GalleryItem>> call = apiInterface.getImage(30);

        call.enqueue(new Callback<ArrayList<GalleryItem>>() {
            @Override
            public void onResponse(Call<ArrayList<GalleryItem>> call, Response<ArrayList<GalleryItem>> response) {
                ArrayList<GalleryItem> galleryItems = response.body();
//                GalleryItem[] items = new GalleryItem[galleryItems.size()];
//                galleryItems.toArray(items);
//                App.getInstance().getDatabase().galleryImageDao().insert(items);
                new InsertImagesToDataBase(galleryItems).execute();
                adapter.addGalleryItems(galleryItems);
            }

            @Override
            public void onFailure(Call<ArrayList<GalleryItem>> call, Throwable t) { }
        });
    }

    private class InsertImagesToDataBase extends AsyncTask<Void, Void, Void> {

        private final GalleryItem[] galleryItems;

        public InsertImagesToDataBase(List<GalleryItem> galleryItems) {
            GalleryItem[] items = new GalleryItem[galleryItems.size()];
            this.galleryItems = galleryItems.toArray(items);

        }

        @Override
        protected Void doInBackground(Void... voids) {
            App.getInstance().getDatabase().galleryImageDao().insert(galleryItems);
            Log.d("test", App.getInstance().getDatabase().galleryImageDao().getAll().size()+"" );
            return null;
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.grid_item_menu:
                recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
                saveDisplayMode("grid");
                return true;
            case R.id.list_item_menu:
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                saveDisplayMode("list");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveDisplayMode(String mode) {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits")
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("myMode", mode);
        editor.apply();
        Toast.makeText(this, "Mode was saved!", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.select_mode_menu, menu);
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("list", adapter.getGalleryItems());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        ArrayList<GalleryItem> imagesList = (ArrayList<GalleryItem>)savedInstanceState.get("list");
        adapter.addGalleryItems(imagesList);
    }
}

