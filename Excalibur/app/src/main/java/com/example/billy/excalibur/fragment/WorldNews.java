package com.example.billy.excalibur.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.billy.excalibur.NewsRecyclerAdapter;
import com.example.billy.excalibur.NyTimesAPIService.NewsWireObjects;
import com.example.billy.excalibur.NyTimesAPIService.NewsWireResults;
import com.example.billy.excalibur.NyTimesAPIService.SearchAPI;
import com.example.billy.excalibur.R;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Billy on 4/19/16.
 */
public class WorldNews extends Fragment {


    public final static String TAG = "World News";
    NewsRecyclerAdapter recycleAdapter;
    RecyclerView recyclerView;
    SearchAPI latestNewsService;
    ArrayList<NewsWireObjects> articleLists;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.recycleview_activity_fragment, container, false);

        setViews(v);
        articleLists = new ArrayList<>();
        retrofitLatestNews();
        return v;
    }

    /**
     * API will need to change to world news query
     */

    private void retrofitLatestNews() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.nytimes.com/svc/news/v3/content/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        latestNewsService = retrofit.create(SearchAPI.class);

        Call<NewsWireResults> call = latestNewsService.listNewsWireResults(10);
        call.enqueue(new Callback<NewsWireResults>() {
            @Override
            public void onResponse(Call<NewsWireResults> call, Response<NewsWireResults> response) {
                NewsWireResults newsWireResults = response.body();

                if (newsWireResults == null) {
                    return;
                }

                articleLists = new ArrayList<>();
                Collections.addAll(articleLists, newsWireResults.getResults());
                Log.i(TAG, articleLists.get(1).getTitle().toString());

                if (recyclerView != null) {
                    recycleAdapter = new NewsRecyclerAdapter(articleLists);
                    recyclerView.setAdapter(recycleAdapter);
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                //recycleAdapter.setData(articleLists);

            }

            @Override
            public void onFailure(Call<NewsWireResults> call, Throwable t) {

            }
        });
    }

    public void setViews(View v) {
        recyclerView = (RecyclerView) v.findViewById(R.id.recycle_view);

    }



}
