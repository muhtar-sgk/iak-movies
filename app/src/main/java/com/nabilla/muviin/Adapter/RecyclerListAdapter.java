package com.nabilla.muviin.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nabilla.muviin.Model.Movie;
import com.nabilla.muviin.R;
import com.nabilla.muviin.Utility.Listener;
import com.nabilla.muviin.Views.DetailActivity;

import java.util.ArrayList;
import java.util.List;

public class RecyclerListAdapter extends RecyclerView.Adapter<RecyclerListAdapter.ViewHolder>{

    private Context context;
    private List<Movie> movieList = new ArrayList<>();

    public RecyclerListAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_detail_movie, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvTitle.setText(movieList.get(position).getTitle());
        holder.tvYear.setText(movieList.get(position).getReleaseDate());
        holder.tvRating.setText(String.valueOf(movieList.get(position).getVoteAverage()));
        final int id = movieList.get(position).getId();

        holder.setListener(new Listener() {
            @Override
            public void onItemClickListener(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("ID", id);
                intent.putExtra("TITLE", movieList.get(position).getTitle());
                context.startActivity(intent);
            }
        });

        Glide.with(context)
                .load(movieList.get(position).getPosterPath())
                .placeholder(R.mipmap.logo)
                .into(holder.imgPoster);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvYear, tvTitle, tvGenre, tvRating;
        ImageView imgPoster;
        Listener listener;

        public ViewHolder(View itemView) {
            super(itemView);

            tvYear = (TextView) itemView.findViewById(R.id.tv_year);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvGenre = (TextView) itemView.findViewById(R.id.tv_genre);
            tvRating = (TextView) itemView.findViewById(R.id.tv_rating);
            imgPoster = (ImageView) itemView.findViewById(R.id.image);

            itemView.setOnClickListener(this);
        }

        private void setListener(Listener listener){
            this.listener = listener;
        }

        @Override
        public void onClick(View v) {
            listener.onItemClickListener(v);
        }
    }
}
