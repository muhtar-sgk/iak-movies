package com.nabilla.muviin.Views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nabilla.muviin.Model.Backdrop;
import com.nabilla.muviin.Model.Movie;
import com.nabilla.muviin.Model.Result;
import com.nabilla.muviin.R;
import com.nabilla.muviin.RestAPI.RestApi;
import com.nabilla.muviin.Utility.DetailPageAdapter;
import com.nabilla.muviin.Utility.Slider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    private ArrayList<Backdrop> backdropList = new ArrayList<>();
    Intent intent;
    int id;
    ViewPager viewPager, vpDetail;
    Toolbar toolbar;
    ActionBar actionBar;
    int currentPage;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        vpDetail = (ViewPager) findViewById(R.id.vpDetail);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        DetailPageAdapter detailPageAdapter = new DetailPageAdapter(getSupportFragmentManager());
        vpDetail.setAdapter(detailPageAdapter);

        tabLayout.setTabTextColors(getResources().getColor(android.R.color.white), getResources().getColor(android.R.color.white));
        tabLayout.setupWithViewPager(vpDetail);

        intent = getIntent();
        id = intent.getIntExtra("ID", 0);
        setTitle(intent.getStringExtra("TITLE"));

        getBackgroundImage();
    }

    private void getBackgroundImage(){
        Call<Result> resultCall = RestApi.restApi.getBackgroundImage(id, getResources().getString(R.string.API_KEY));
        resultCall.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, final Response<Result> response) {
                final Result result = response.body();

                if (response.isSuccessful()){
                    Backdrop bd;
                    for (int i=0; i < result.getBackgroundImages().size(); i++) {
                        bd = new Backdrop();

                        String backdrop = result.getBackgroundImages().get(i).getFilePath();
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

                        viewPager.setAdapter(new Slider(DetailActivity.this, backdropList));
                    }
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

            }
        });
    }

    public int getId(){
        intent = getIntent();
        id = intent.getIntExtra("ID", 0);

        return id;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home){
            super.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
