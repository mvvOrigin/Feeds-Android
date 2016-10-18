package com.example.feeds.model;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

public interface ApiService {

    @GET("feeds.json")
    Observable<List<Feed>> getFeeds();

    @FormUrlEncoded @POST("feed/liked")
    Observable<String> onLiked(@Field("id") int id, @Field("isLike") boolean isLike);

    @FormUrlEncoded @POST("feed/shared")
    Observable<String> onShared(@Field("id") int id);
}
