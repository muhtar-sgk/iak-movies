package com.nabilla.muviin.Views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nabilla.muviin.Adapter.RecyclerCastAdapter;
import com.nabilla.muviin.Model.Cast;
import com.nabilla.muviin.Model.Result;
import com.nabilla.muviin.R;
import com.nabilla.muviin.RestAPI.RestApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CastFragment extends Fragment {

    int id;
    private RecyclerView recyclerView;
    private RecyclerCastAdapter adapter;
    private List<Cast> castList = new ArrayList<>();

    public CastFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_cast, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);

        DetailActivity detailActivity = (DetailActivity) getActivity();
        id = detailActivity.getId();

        getDataArtist(id);

        return view;
    }

    private void getDataArtist(int id){
        Call<Result> resultCall = RestApi.restApi.getArtist(id, getResources().getString(R.string.API_KEY));
        resultCall.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Result cast = response.body();

                Cast cast1;
                for (int i=0; i<cast.getCast().size(); i++){
                    cast1 = new Cast();

                    String name = cast.getCast().get(i).getName();
                    String character = cast.getCast().get(i).getCharacter();
                    String poster = cast.getCast().get(i).getProfilePath();

                    cast1.setId(cast.getCast().get(i).getId());
                    cast1.setName(name);
                    cast1.setCharacter(character);
                    cast1.setProfilePath(poster);

                    castList.add(cast1);
                }

                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                adapter = new RecyclerCastAdapter(getContext(), castList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

            }
        });
    }
}
