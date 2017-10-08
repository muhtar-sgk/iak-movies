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


public class RecyclerItemAdapter extends RecyclerView.Adapter<RecyclerItemAdapter.ViewHolder>{
    private Context context;
    private List<Movie> movieList = new ArrayList<>();

    public RecyclerItemAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    @Override
    public RecyclerItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_small_image, parent, false);

        return new RecyclerItemAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerItemAdapter.ViewHolder holder, final int position) {
        Glide.with(context)
                .load(movieList.get(position).getPosterPath())
                .fitCenter()
                .placeholder(R.mipmap.logo)
                .into(holder.imageView);

        final int id = movieList.get(position).getId();

        holder.setListener(new Listener() {
            @Override
            public void onItemClickListener(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("ID", id);
                intent.putExtra("TITLE", movieList.get(position).getTitle());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView imageView;
        Listener listener;
        TextView tvImage;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.imageView);

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
