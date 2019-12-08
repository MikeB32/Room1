package com.task.room.networking;

import com.task.room.model.NewsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApi {
    @GET("search?")
    Call<NewsResponse> getNewsList(@Query("page") int pageNo,
                                   @Query("api-key") String apiKey);
}
