package com.nabilla.muviin.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nabilla.muviin.Model.Review;
import com.nabilla.muviin.R;

import java.util.ArrayList;
import java.util.List;


public class RecyclerReviewAdapter extends RecyclerView.Adapter<RecyclerReviewAdapter.ViewHolder>{

    private Context context;
    private List<Review> reviewList = new ArrayList<>();

    public RecyclerReviewAdapter(Context context, List<Review> reviewList) {
        this.context = context;
        this.reviewList = reviewList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_review, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Review review = reviewList.get(position);

        if (reviewList.size()>0){
            holder.tvAuthor.setText(review.getAuthor());
            holder.tvDesc.setText(review.getContent());
        }else{
            holder.tvAuthor.setText("Unfortunately, cannot find any review for this movies");
        }

    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvAuthor, tvDesc;

        public ViewHolder(View itemView) {
            super(itemView);

            tvAuthor = (TextView) itemView.findViewById(R.id.tvAuthor);
            tvDesc = (TextView) itemView.findViewById(R.id.tvDescription);
        }
    }
}
