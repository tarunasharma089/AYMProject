package com.aym.model.data;

import com.aym.model.entity.ZomatoResponse;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import rx.Observable;

/**
 * Created by Taruna on 2018/5/19.
 */
public interface RetroService {
    String BASE_URL = "https://developers.zomato.com/api/v2.1/";

    @Headers("user-key: 8cae63ac0228f1b41e5e8afa4ccd35eb")
    @GET("search")
    Observable<ZomatoResponse<List<ZomatoResponse.Restaurant>>> getRestaurants();
}
