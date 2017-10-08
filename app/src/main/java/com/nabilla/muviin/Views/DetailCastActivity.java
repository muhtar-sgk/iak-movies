package com.nabilla.muviin.Views;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.nabilla.muviin.Adapter.RecyclerItemAdapter;
import com.nabilla.muviin.Adapter.RecyclerTrailerAdapter;
import com.nabilla.muviin.Model.Backdrop;
import com.nabilla.muviin.Model.Cast;
import com.nabilla.muviin.Model.DetailCast;
import com.nabilla.muviin.Model.Movie;
import com.nabilla.muviin.Model.Result;
import com.nabilla.muviin.Model.ReviewResults;
import com.nabilla.muviin.R;
import com.nabilla.muviin.RestAPI.RestApi;
import com.nabilla.muviin.Utility.Slider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailCastActivity extends AppCompatActivity {

    private ArrayList<Backdrop> backdropList = new ArrayList<>();
    ViewPager viewPager;
    TextView txtBiography, txtBirthPlace, txtBorn, txtKnownAs;
    int id;
    Toolbar toolbar;
    ActionBar actionBar;
    int currentPage;
    private RecyclerView recyclerView;
    private RecyclerItemAdapter adapter;
    private List<Movie> movieList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_cast);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        txtBiography = (TextView) findViewById(R.id.txtBiography);
        txtBirthPlace = (TextView) findViewById(R.id.txtBirthPlace);
        txtBorn = (TextView) findViewById(R.id.txtBornDate);
        txtKnownAs = (TextView) findViewById(R.id.txtKnown);

        Intent intent = getIntent();
        id = intent.getIntExtra("ID", 0);
        setTitle(intent.getStringExtra("NAME"));

        getData(id);
        getBackgroundImage(id);
        getRecommendation(id);
    }

    private void getData(int id){
        Call<DetailCast> detailCastCall = RestApi.restApi.getDetailCast(id, getResources().getString(R.string.API_KEY));
        detailCastCall.enqueue(new Callback<DetailCast>() {
            @Override
            public void onResponse(Call<DetailCast> call, Response<DetailCast> response) {
                DetailCast body = response.body();

                String[] data = new String[body.getAlsoKnownAs().size()];
                for (int i=0; i < data.length; i++){
                    data[i] = body.getAlsoKnownAs().get(i);
                    txtKnownAs.setText(Arrays.toString(data).replaceAll("\\[|\\]",""));
                }

                if(body.getBiography().isEmpty()){
                    txtBiography.setText("No Biography entered");
                }else{
                    txtBiography.setText(body.getBiography());
                }

                txtBirthPlace.setText(body.getPlaceOfBirth());
                txtBorn.setText(body.getBirthday());
            }

            @Override
            public void onFailure(Call<DetailCast> call, Throwable t) {

            }
        });
    }

    private void getBackgroundImage(int id){
        Call<Result> resultCall = RestApi.restApi.getCastImage(id, getResources().getString(R.string.API_KEY));
        Log.d("TEST", String.valueOf(resultCall.request().url()));
        resultCall.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, final Response<Result> response) {
                final Result result = response.body();

                if (response.isSuccessful()){
                    Backdrop bd;
                    for (int i=0; i < result.getProfiles().size(); i++) {
                        bd = new Backdrop();

                        String backdrop = result.getProfiles().get(i).getFilePath();
                        bd.setFilePath(backdrop);
                        backdropList.add(bd);
                    }

                    if (backdropList.size() > 0){

                        final Handler handler = new Handler();
                        final Runnable update = new Runnable() {
                            public void run() {
                                if (currentPage == backdropList.size()) {
                                    currentPage = 0;
                                } else {
                                    currentPage = currentPage + 1;
                                }
                                viewPager.setCurrentItem(currentPage, true);
                            }
                        };

                        new Timer().schedule(new TimerTask() {

                            @Override
                            public void run() {
                                handler.post(update);
                            }
                        }, 3000, 8000);

                        viewPager.setAdapter(new Slider(DetailCastActivity.this, backdropList));
                    }
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
            super.finish();
        }

        return super.onOptionsItemSelected(item);
    }
    private void getRecommendation(int id){
        Call<ReviewResults> movieCall = RestApi.restApi.getCastMovie(id, getResources().getString(R.string.API_KEY));
        Log.d("TEST", String.valueOf(movieCall.request().url()));
        movieCall.enqueue(new Callback<ReviewResults>() {
            @Override
            public void onResponse(Call<ReviewResults> call, Response<ReviewResults> response) {
                ReviewResults body = response.body();

                Movie movie;
                for (int i = 0; i< body.getCast().size(); i++){
                    movie = new Movie();

                    String title = body.getCast().get(i).getTitle();
                    String poster = body.getCast().get(i).getPosterPath();
                    Integer id = body.getCast().get(i).getId();

                    movie.setId(id);
                    movie.setTitle(title);
                    movie.setPosterPath(poster);

                    movieList.add(movie);
                }

                recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
                adapter = new RecyclerItemAdapter(getApplicationContext(), movieList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ReviewResults> call, Throwable t) {

            }
        });
    }
}
