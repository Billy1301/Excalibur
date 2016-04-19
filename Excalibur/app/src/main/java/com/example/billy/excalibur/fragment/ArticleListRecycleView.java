package com.example.billy.excalibur.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.billy.excalibur.MainActivity;
import com.example.billy.excalibur.NewsRecyclerAdapter;
import com.example.billy.excalibur.NyTimesAPIService.NewsWireObjects;
import com.example.billy.excalibur.NyTimesAPIService.NewsWireResults;
import com.example.billy.excalibur.NyTimesAPIService.PreloadTenArticles;
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
 * Created by Billy on 4/18/16.
 */
public class ArticleListRecycleView extends Fragment {

    public final static String TAG = "ArticleRecycleView";

    NewsRecyclerAdapter recycleAdapter;
    RecyclerView recyclerView;
    SearchAPI latestNewsService;
    Toolbar toolbar;
    public ArrayList<NewsWireObjects> articleLists;

    private String sections;

    public void setSections(String sections) {
        this.sections = sections;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.recycleview_activity_fragment, container, false);

        setViews(v);
        articleLists = new ArrayList<>();
        retrofitLatestNews();

        return v;
    }


    private void retrofitLatestNews() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.nytimes.com/svc/news/v3/content/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        latestNewsService = retrofit.create(SearchAPI.class);

        Call<NewsWireResults> call = latestNewsService.listNewsWireResults(sections, 10);
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
