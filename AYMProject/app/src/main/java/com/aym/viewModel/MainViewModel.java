package com.aym.viewModel;

import android.databinding.ObservableField;
import android.util.Log;
import android.view.View;

import com.aym.model.entity.ZomatoResponse;
import com.aym.model.data.RetrofitHelper;
import com.aym.view.CompletedListener;
import com.aym.view.MovieAdapter;

import rx.Subscriber;

/**
 * Created by Taruna on 2018/5/19.
 */
public class MainViewModel {
    public ObservableField<Integer> contentViewVisibility;
    public ObservableField<Integer> progressBarVisibility;
    public ObservableField<Integer> errorInfoLayoutVisibility;
    public ObservableField<String> exception;
    private Subscriber<ZomatoResponse.Restaurant> subscriber;
    private MovieAdapter movieAdapter;
    private int page;
    private CompletedListener completedListener;

    public MainViewModel(MovieAdapter movieAdapter,CompletedListener completedListener, int page) {
        this.movieAdapter = movieAdapter;
        this.completedListener = completedListener;
        this.page = page;
        initData();
        getRestaurants();
    }

    private void getRestaurants() {
        subscriber = new Subscriber<ZomatoResponse.Restaurant>() {
            @Override
            public void onCompleted() {
                Log.d("[MainViewModel]", "onCompleted");
                hideAll();
                contentViewVisibility.set(View.VISIBLE);
                completedListener.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                hideAll();
                errorInfoLayoutVisibility.set(View.VISIBLE);
                exception.set(e.getMessage());
            }

            @Override
            public void onNext(ZomatoResponse.Restaurant movie) {
                movieAdapter.addItem(movie.getRestaurant());
            }
        };
        RetrofitHelper.getInstance().getRestaurants(subscriber);
    }

    public void refreshData() {
        getRestaurants();
    }

    private void initData() {
        contentViewVisibility = new ObservableField<>();
        progressBarVisibility = new ObservableField<>();
        errorInfoLayoutVisibility = new ObservableField<>();
        exception = new ObservableField<>();
        contentViewVisibility.set(View.GONE);
        errorInfoLayoutVisibility.set(View.GONE);
        progressBarVisibility.set(View.VISIBLE);
    }

    private void hideAll(){
        contentViewVisibility.set(View.GONE);
        errorInfoLayoutVisibility.set(View.GONE);
        progressBarVisibility.set(View.GONE);
    }
}
