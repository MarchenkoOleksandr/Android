package com.example.android_kurs;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface UnSplashApiService {
//    @Headers("Authorization: Client-ID 810ccfc599f8854496497bf2c12a733f2c01658a40c1afa500a41db7808b1bb4")
    @Headers("Authorization: Client-ID 8a0616194496fb65079999324976a3cd0ae90b58209370a61e3952cfe79bd85b")
    @GET("photos/random")
    Call<ArrayList<GalleryItem>> getImage(@Query("count") int count);
}
