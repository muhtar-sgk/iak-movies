package com.nabilla.muviin.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ReviewResults {
    @SerializedName("results")
    @Expose
    private List<Review> results = new ArrayList<>();
    private List<Movie> cast = new ArrayList<>();

    public List<Movie> getCast() {
        return cast;
    }
    public List<Review> getResults() {
        return results;
    }
}
