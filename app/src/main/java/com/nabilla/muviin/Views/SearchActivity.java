package com.nabilla.muviin.Views;

import android.app.SearchManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

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

public class SearchActivity extends AppCompatActivity{

    RecyclerView recyclerView;
    private List<Movie> movieList = new ArrayList<>();
    private RecyclerListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            getSearchingData(query);
            setTitle(query);
        }
    }

    private void getSearchingData(String query){
        Call<Result> calMovie = RestApi.restApi.getSearching(getResources().getString(R.string.API_KEY),
                query);
        calMovie.enqueue(new Callback<Result>() {
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
                    movie.setPosterPath("http://image.tmdb.org/t/p/w185"+poster);

                    movieList.add(movie);
                }

                if (movieList.size() > 0){
                    adapter = new RecyclerListAdapter(SearchActivity.this, movieList);
                    recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
