package com.nabilla.muviin.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nabilla.muviin.Interface.OnClickedListener;
import com.nabilla.muviin.Model.Trailer;
import com.nabilla.muviin.R;

import java.util.ArrayList;
import java.util.List;



public class RecyclerTrailerAdapter extends RecyclerView.Adapter<RecyclerTrailerAdapter.ViewHolder> {

    private Context context;
    private List<Trailer> videoList = new ArrayList<>();

    public RecyclerTrailerAdapter(Context context, List<Trailer> videoList) {
        this.context = context;
        this.videoList = videoList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_video, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Trailer video = videoList.get(position);

        Glide.with(context)
                .load("http://img.youtube.com/vi/" + video.getKey() + "/mqdefault.jpg")
                .into(holder.ivVideo);
        holder.setItemClick(new OnClickedListener() {
            @Override
            public void onClickedListener(int selectedPos) {

            }

            @Override
            public void onItemClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + video.getKey()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView ivVideo;
        TextView tvName, tvType;
        OnClickedListener itemClick;


        public ViewHolder(View itemView) {
            super(itemView);

            ivVideo = (ImageView) itemView.findViewById(R.id.imgVideo);

            itemView.setOnClickListener(this);
        }

        public void setItemClick(OnClickedListener itemClick) {
            this.itemClick = itemClick;
        }

        @Override
        public void onClick(View v) {
            itemClick.onItemClick(v);
        }
    }
}
