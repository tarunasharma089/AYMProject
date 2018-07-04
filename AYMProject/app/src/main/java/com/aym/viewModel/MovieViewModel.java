package com.aym.viewModel;

import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.aym.model.entity.ZomatoResponse;
import com.bumptech.glide.Glide;

/**
 * Created by Taruna on 2018/5/19.
 */
public class MovieViewModel extends BaseObservable {
    private ZomatoResponse.Restaurant_ movie;

    public MovieViewModel(ZomatoResponse.Restaurant_ movie) {
        this.movie = movie;
    }



    public String getTitle() {
        return movie.getName();
    }

    public float getRating() {
        return Float.valueOf(movie.getUserRating().getAggregateRating());
    }

    public String getRatingText(){
        return "Price for two: "+movie.getCurrency()+String.valueOf(movie.getAverageCostForTwo());
    }



    public String getMovieType() {

        return movie.getCuisines();
    }

    public String getImageUrl() {
        return movie.getThumb();
    }

    @BindingAdapter({"app:imageUrl"})
    public static void loadImage(ImageView imageView,String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .into(imageView);

    }



}
