package com.nabilla.muviin.Views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nabilla.muviin.Adapter.RecyclerItemAdapter;
import com.nabilla.muviin.Adapter.RecyclerTrailerAdapter;
import com.nabilla.muviin.Model.Movie;
import com.nabilla.muviin.Model.Result;
import com.nabilla.muviin.Model.Trailer;
import com.nabilla.muviin.Model.TrailerResult;
import com.nabilla.muviin.R;
import com.nabilla.muviin.RestAPI.RestApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoFragment extends Fragment {

    ImageView imgPoster;
    TextView txtTitle, txtYear, txtGenres, txtOverview, txtRating, txtStatus, txtBudget, txtDuration;
    String[] data;
    private RecyclerView recyclerView, trailerRecyclerview;
    private RecyclerItemAdapter adapter;
    private RecyclerTrailerAdapter trailerAdapter;
    private List<Movie> movieList = new ArrayList<>();
    private List<Trailer> videoList = new ArrayList<>();

    public InfoFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        imgPoster = (ImageView) view.findViewById(R.id.imgPoster);
        txtTitle = (TextView) view.findViewById(R.id.txtTitle);
        txtYear = (TextView) view.findViewById(R.id.txtYear);
        txtOverview = (TextView) view.findViewById(R.id.txtOverview);
        txtGenres = (TextView) view.findViewById(R.id.txtGenres);
        txtRating = (TextView) view.findViewById(R.id.txtRating);
        txtDuration = (TextView) view.findViewById(R.id.tvDuration);
        txtStatus = (TextView) view.findViewById(R.id.tvStatus);
        txtBudget = (TextView) view.findViewById(R.id.tvBudget);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        trailerRecyclerview = (RecyclerView) view.findViewById(R.id.trailerRecycler);

        DetailActivity detailActivity = (DetailActivity) getActivity();
        int ide = detailActivity.getId();
        getDetail(ide);
        getRecommendation(ide);
        getTrailer(ide);

        return view;
    }

    private void getDetail(int ide){
        Call<Movie> resultCall = RestApi.restApi.getDetailMovie(ide, getResources().getString(R.string.API_KEY));

        resultCall.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {

                Movie body = response.body();

                if (response.isSuccessful()){
                    String title = body.getTitle();
                    String poster = body.getPosterPath();
                    String releaseDate = body.getReleaseDate();
                    String status = body.getStatus();
                    int budget = body.getBudget();
                    int runtime = body.getRuntime();

                    int hours = runtime / 60;
                    int minutes = runtime % 60;

                    String overview = body.getOverview();
                    Double rating = body.getVoteAverage();

                    Glide.with(getContext())
                            .load("http://image.tmdb.org/t/p/w185"+poster)
                            .into(imgPoster);

                    data = new String[body.getGenres().size()];
                    for (int i=0; i < data.length; i++){
                        data[i] = body.getGenres().get(i).getName();
                        txtGenres.setText(Arrays.toString(data).replaceAll("\\[|\\]",""));
                    }

                    txtTitle.setText(title);
                    txtYear.setText(releaseDate);
                    txtOverview.setText(overview);
                    //txtRating.setText(String.valueOf(rating));
                    txtDuration.setText(String.format("%d hours %2d minute", hours, minutes));
                    txtBudget.setText("$ "+budget);
                    txtStatus.setText(status);
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Log.d("TEST", String.valueOf(t));
            }
        });
    }
    private void getRecommendation(int id){
        Call<Result> movieCall = RestApi.restApi.getRecommendationMovie(id, getResources().getString(R.string.API_KEY));
        movieCall.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Result body = response.body();

                Movie movie;
                for (int i = 0; i< body.getResults().size(); i++){
                    movie = new Movie();

                    String title = body.getResults().get(i).getTitle();
                    String poster = body.getResults().get(i).getPosterPath();
                    Integer id = body.getResults().get(i).getId();

                    movie.setId(id);
                    movie.setTitle(title);
                    movie.setPosterPath(poster);

                    movieList.add(movie);
                }

                recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                        LinearLayoutManager.HORIZONTAL, false));
                adapter = new RecyclerItemAdapter(getContext(), movieList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

            }
        });
    }
    private void getTrailer(int id){
        Call<TrailerResult> trailerCall = RestApi.restApi.getTrailer(id, getResources().getString(R.string.API_KEY));
        trailerCall.enqueue(new Callback<TrailerResult>() {
            @Override
            public void onResponse(Call<TrailerResult> call, Response<TrailerResult> response) {
                TrailerResult result = response.body();

                Trailer video;
                for (int i = 0; i<result.getResults().size(); i++){
                    video = new Trailer();

                    String name = result.getResults().get(i).getName();
                    String type = result.getResults().get(i).getType();
                    String key = result.getResults().get(i).getKey();

                    video.setKey(key);
                    video.setName(name);
                    video.setType(type);

                    videoList.add(video);
                }

                trailerRecyclerview.setLayoutManager(new LinearLayoutManager(getContext(),
                        LinearLayoutManager.HORIZONTAL, false));
                trailerAdapter = new RecyclerTrailerAdapter(getContext(), videoList);
                trailerRecyclerview.setAdapter(trailerAdapter);
            }

            @Override
            public void onFailure(Call<TrailerResult> call, Throwable t) {

            }
        });
    }
}
