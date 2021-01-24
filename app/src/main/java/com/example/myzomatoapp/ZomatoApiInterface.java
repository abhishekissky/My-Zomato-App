package com.example.myzomatoapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ZomatoApiInterface {

    @Headers("user-key:1b3c8b37ea96785391fa55c288ac385c")
    @GET("/api/v2.1/search")
    Call<Zomato> getData(
            @Query("lat") String lat,
            @Query("lon") String lon
    );
//https://developers.zomato.com/api/v2.1/search?q=kanpur
    @Headers("user-key:1b3c8b37ea96785391fa55c288ac385c")
    @GET("/api/v2.1/search")
    Call<Zomato> getSearchData(
            @Query("q")String query
    );
}

