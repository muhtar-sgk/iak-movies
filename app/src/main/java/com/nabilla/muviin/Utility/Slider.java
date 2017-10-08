package com.nabilla.muviin.Utility;


import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nabilla.muviin.Model.Backdrop;
import com.nabilla.muviin.R;

import java.util.ArrayList;

public class Slider extends PagerAdapter{

    Context context;
    private ArrayList<Backdrop> images;
    LayoutInflater layoutInflater;

    public Slider(Context context, ArrayList<Backdrop> images) {
        this.context = context;
        this.images=images;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public Object instantiateItem(ViewGroup parent, final int position) {
        View view = layoutInflater.inflate(R.layout.item_image_movie, parent, false);
        ImageView myImage = (ImageView) view.findViewById(R.id.imageView);
        Glide.with(context)
                .load(images.get(position).getFilePath())
                .fitCenter()
                .into(myImage);

        parent.addView(view, 0);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}
