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
import com.nabilla.muviin.Interface.OnClickedListener;
import com.nabilla.muviin.Model.Cast;
import com.nabilla.muviin.R;
import com.nabilla.muviin.Views.DetailActivity;
import com.nabilla.muviin.Views.DetailCastActivity;

import java.util.List;


public class RecyclerCastAdapter extends RecyclerView.Adapter<RecyclerCastAdapter.ViewHolder> {

    private Context context;
    private List<Cast> castList;

    public RecyclerCastAdapter(Context context, List<Cast> castList) {
        this.context = context;
        this.castList = castList;
    }

    @Override
    public RecyclerCastAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_artist, parent, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerCastAdapter.ViewHolder holder, int position) {
        final Cast cast = castList.get(position);

        holder.txtName.setText(cast.getName());
        holder.txtCharacter.setText("As "+cast.getCharacter());
        final int id = cast.getId();

        Glide.with(context)
                .load(cast.getProfilePath())
                .placeholder(R.mipmap.logo)
                .into(holder.imgArtist);

        holder.setOnClickedListener(new OnClickedListener() {
            @Override
            public void onClickedListener(int selectedPos) {

            }

            @Override
            public void onItemClick(View view) {
                Intent intent = new Intent(context, DetailCastActivity.class);
                intent.putExtra("ID", id);
                intent.putExtra("NAME", cast.getName());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return castList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView txtName, txtCharacter;
        ImageView imgArtist;
        OnClickedListener onClickedListener;

        public ViewHolder(View itemView) {
            super(itemView);

            txtName = (TextView) itemView.findViewById(R.id.txtName);
            txtCharacter = (TextView) itemView.findViewById(R.id.txtCharacter);
            imgArtist = (ImageView) itemView.findViewById(R.id.imgArtist);

            itemView.setOnClickListener(this);
        }

        public void setOnClickedListener(OnClickedListener onClickedListener) {
            this.onClickedListener = onClickedListener;
        }

        @Override
        public void onClick(View v) {
            onClickedListener.onItemClick(v);
        }
    }
}
