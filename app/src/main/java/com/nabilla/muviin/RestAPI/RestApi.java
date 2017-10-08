package com.nabilla.muviin.RestAPI;

import com.nabilla.muviin.Model.Cast;
import com.nabilla.muviin.Model.DetailCast;
import com.nabilla.muviin.Model.Movie;
import com.nabilla.muviin.Model.Result;
import com.nabilla.muviin.Model.ReviewResults;
import com.nabilla.muviin.Model.TrailerResult;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RestApi {

    String BASE_URL = "https://api.themoviedb.org/3/";

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    RestApi restApi = retrofit.create(RestApi.class);

    @GET("movie/now_playing")
    Call<Result> getMovieNowPlaying(@Query("api_key") String api_key);
    @GET("movie/upcoming")
    Call<Result> getMovieComingSoon(@Query("api_key") String api_key);
    @GET("movie/top_rated")
    Call<Result> getMovieTopRated(@Query("api_key") String api_key);
    @GET("movie/{movie_id}")
    Call<Movie> getDetailMovie(@Path("movie_id") int movie_id, @Query("api_key") String api_key);
    @GET("movie/{movie_id}/images")
    Call<Result> getBackgroundImage(@Path("movie_id") int movie_id, @Query("api_key") String api_key);
    @GET("person/{person_id}/images")
    Call<Result> getCastImage(@Path("person_id") int movie_id, @Query("api_key") String api_key);
    @GET("search/movie")
    Call<Result> getSearching(@Query("api_key") String api_key, @Query("query") String query);
    @GET("movie/{movie_id}/credits")
    Call<Result> getArtist(@Path("movie_id") int movie_id, @Query("api_key") String api_key);
    @GET("movie/{movie_id}/reviews")
    Call<ReviewResults> getReviews(@Path("movie_id") int movie_id, @Query("api_key") String api_key);
    @GET("movie/{movie_id}/recommendations")
    Call<Result> getRecommendationMovie(@Path("movie_id") int movie_id, @Query("api_key") String api_key);
    @GET("movie/{movie_id}/videos")
    Call<TrailerResult> getTrailer(@Path("movie_id") int movie_id, @Query("api_key") String api_key);
    @GET("person/{person_id}")
    Call<DetailCast> getDetailCast(@Path("person_id") int person_id, @Query("api_key") String api_key);
    @GET("person/{person_id}/movie_credits")
    Call<ReviewResults> getCastMovie(@Path("person_id") int person_id, @Query("api_key") String api_key);

    /*Belum dibikin pisan*/
    @GET("genre/movie/list")
    Call<Result> getGenreName(@Query("api_key") String api_key);
}
