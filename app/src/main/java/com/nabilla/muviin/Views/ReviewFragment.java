package com.nabilla.muviin.Views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nabilla.muviin.Adapter.RecyclerReviewAdapter;
import com.nabilla.muviin.Model.Review;
import com.nabilla.muviin.Model.ReviewResults;
import com.nabilla.muviin.R;
import com.nabilla.muviin.RestAPI.RestApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerReviewAdapter adapter;
    private List<Review> reviewList = new ArrayList<>();
    int id;

    public ReviewFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.content_review, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);

        DetailActivity detailActivity = (DetailActivity) getActivity();
        id = detailActivity.getId();
        getReview(id);

        return view;
    }

    private void getReview(int id){
        Call<ReviewResults> resultsCall = RestApi.restApi.getReviews(id, getResources().getString(R.string.API_KEY));
        resultsCall.enqueue(new Callback<ReviewResults>() {
            @Override
            public void onResponse(Call<ReviewResults> call, Response<ReviewResults> response) {
                ReviewResults review = response.body();

                Review review1;
                for (int i = 0; i < review.getResults().size(); i++){
                    review1 = new Review();

                    String author = review.getResults().get(i).getAuthor();
                    String desc = review.getResults().get(i).getContent().trim();

                    review1.setAuthor(author);
                    review1.setContent(desc);

                    reviewList.add(review1);
                }

                Log.d("TEST", String.valueOf(4546565));

                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                adapter = new RecyclerReviewAdapter(getContext(), reviewList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ReviewResults> call, Throwable t) {

            }
        });
    }
}
