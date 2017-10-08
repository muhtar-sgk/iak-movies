package com.nabilla.muviin.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TrailerResult {
    @SerializedName("results")
    @Expose
    private List<Trailer> results = new ArrayList<>();

    public List<Trailer> getResults() {
        return results;
    }
}
