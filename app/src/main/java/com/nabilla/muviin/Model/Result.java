package com.nabilla.muviin.Model;


import java.util.ArrayList;
import java.util.List;

public class Result {
    private List<Movie> results = new ArrayList<>();
    private List<Backdrop> backdrops = new ArrayList<>();
    private List<Backdrop> profiles = new ArrayList<>();
    private List<Genre> genres = new ArrayList<>();
    private List<Cast> cast = new ArrayList<>();

    public List<Movie> getResults() {
        return results;
    }

    public List<Backdrop> getBackgroundImages() {
        return backdrops;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public List<Cast> getCast() {
        return cast;
    }

    public List<Backdrop> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<Backdrop> profiles) {
        this.profiles = profiles;
    }
}
