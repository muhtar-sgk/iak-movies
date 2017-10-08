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
import com.nabilla.muviin.Model.Movie;
import com.nabilla.muviin.Model.Result;
import com.nabilla.muviin.R;
import com.nabilla.muviin.RestAPI.RestApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieComingSoon extends Fragment {

    private RecyclerView recyclerView;
    private List<Movie> movieList = new ArrayList<>();
    private RecyclerListAdapter adapter;

    public MovieComingSoon() {

    }

    public static MovieComingSoon newInstance(String param1, String param2) {
        MovieComingSoon fragment = new MovieComingSoon();
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
        View view =  inflater.inflate(R.layout.fragment_movie_coming_soon, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        getComingSoon();

        return view;
    }

    private void getComingSoon(){
        Call<Result> callComingSoon = RestApi.restApi.getMovieComingSoon(getResources().getString(R.string.API_KEY));
        callComingSoon.enqueue(new Callback<Result>() {
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

                if (movieList.size() > 0){
                    adapter = new RecyclerListAdapter(getContext(), movieList);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(adapter);
                }

            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.d("TEST", String.valueOf(t));

            }
        });
    }
}
