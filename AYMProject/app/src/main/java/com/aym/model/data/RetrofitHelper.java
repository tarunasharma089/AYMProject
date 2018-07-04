package com.aym.model.data;

import com.aym.model.entity.ZomatoResponse;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Taruna on 2018/5/19.
 */
public class RetrofitHelper {
    private static final int DEFAULT_TIMEOUT = 10;
    private Retrofit retrofit;
    private RetroService weatherService;
    OkHttpClient.Builder builder;

    /**
     * RetrofitHelper
     * */
    private static class Singleton {
        private static final RetrofitHelper INSTANCE = new RetrofitHelper();
    }

    public static RetrofitHelper getInstance() {
        return Singleton.INSTANCE;
    }

    private RetrofitHelper() {
        builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(RetroService.BASE_URL)
                .build();
        weatherService = retrofit.create(RetroService.class);
    }

    public void getRestaurants(Subscriber<ZomatoResponse.Restaurant> subscriber) {
        weatherService.getRestaurants()
                .map(new Func1<ZomatoResponse<List<ZomatoResponse.Restaurant>>, List<ZomatoResponse.Restaurant>>() {
                    @Override
                    public List<ZomatoResponse.Restaurant> call(ZomatoResponse<List<ZomatoResponse.Restaurant>> listResponse) {
                        return listResponse.getRestaurants();
                    }
                })
                .flatMap(new Func1<List<ZomatoResponse.Restaurant>, Observable<ZomatoResponse.Restaurant>>() {
                    @Override
                    public Observable<ZomatoResponse.Restaurant> call(List<ZomatoResponse.Restaurant> movies) {
                        return Observable.from(movies);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
