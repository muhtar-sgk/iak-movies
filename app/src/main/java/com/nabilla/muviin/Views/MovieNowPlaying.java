package com.nabilla.muviin.Views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nabilla.muviin.Adapter.RecyclerListAdapter;
import com.nabilla.muviin.Model.Genre;
import com.nabilla.muviin.Model.Movie;
import com.nabilla.muviin.Model.Result;
import com.nabilla.muviin.R;
import com.nabilla.muviin.RestAPI.RestApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieNowPlaying extends Fragment {

    private RecyclerView recyclerView;
    private List<Movie> movieList = new ArrayList<>();
    private RecyclerListAdapter adapter;
    private List<Integer> integerList = new ArrayList<>();
    private List<Genre> genreList = new ArrayList<>();
    int[] d;

    public MovieNowPlaying() {

    }

    public static MovieNowPlaying newInstance(String param1, String param2) {
        MovieNowPlaying fragment = new MovieNowPlaying();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_movie_now_playing, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);

        getGenre();
        getNowPlaying();
        return view;
    }

    private void getNowPlaying(){
        Call<Result> callNowPlaying = RestApi.restApi.getMovieNowPlaying(getResources().getString(R.string.API_KEY));
        callNowPlaying.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Result body = response.body();

                Movie movie;
                for (int i = 0; i< body.getResults().size(); i++){
                    movie = new Movie();

                    String title = body.getResults().get(i).getTitle();
                    Double vote = body.getResults().get(i).getVoteAverage();
                    String release = body.getResults().get(i).getReleaseDate();
                    String poster = body.getResults().get(i).getPosterPath();
                    Integer id = body.getResults().get(i).getId();

                    movie.setId(id);
                    movie.setTitle(title);
                    movie.setVoteAverage(vote);
                    movie.setReleaseDate(release);
                    movie.setPosterPath(poster);

                    movieList.add(movie);
                }

                adapter = new RecyclerListAdapter(getContext(), movieList);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.d("TEST", String.valueOf(t));
            }
        });
    }

    private void getGenre(){
        Call<Result> call = RestApi.restApi.getGenreName(getResources().getString(R.string.API_KEY));
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Result result = response.body();

                Genre genres;
                for (int i=0; i<result.getGenres().size(); i++){
                    genres = new Genre();
                    String genre = result.getGenres().get(i).getName();
                    int id = result.getGenres().get(i).getId();

                    genres.setId(id);
                    genres.setName(genre);

                    genreList.add(genres);
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

            }
        });
    }
}
