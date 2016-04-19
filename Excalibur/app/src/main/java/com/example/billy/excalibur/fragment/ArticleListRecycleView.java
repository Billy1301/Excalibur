package com.example.billy.excalibur.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
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
import com.example.billy.excalibur.Adaptors.NewsRecyclerAdapter;
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
 * Created by Billy on 4/18/16.
 */
public class ArticleListRecycleView extends Fragment {

    public final static String TAG = "ArticleRecycleView";

    NewsRecyclerAdapter recycleAdapter;
    RecyclerView recyclerView;
    SearchAPI latestNewsService;
    FrameLayout fragContainer;
    FloatingActionButton fab;
    Toolbar toolbar;

    DrawerLayout drawer;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    ArticleStory articleFragment;
    ArticleListRecycleView articleListRecycleView;
    public static ArrayList<NewsWireObjects> articleLists;

    Button getNewsButton;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.recycleview_activity_fragment, container, false);

        setViews(v);


        articleLists = new ArrayList<>();
        articleLists.addAll(MainActivity.articleLists);


        if (recyclerView != null) {
            recycleAdapter = new NewsRecyclerAdapter(articleLists);
            recyclerView.setAdapter(recycleAdapter);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //retrofitLatestNews();
        onClicker();


        return v;
    }


    private void retrofitLatestNews() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.nytimes.com/svc/news/v3/content/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        latestNewsService = retrofit.create(SearchAPI.class);

        Call<NewsWireResults> call = latestNewsService.listNewsWireResults("us", 10);
        call.enqueue(new Callback<NewsWireResults>() {
            @Override
            public void onResponse(Call<NewsWireResults> call, Response<NewsWireResults> response) {
                NewsWireResults newsWireResults = response.body();

                if (newsWireResults == null) {
                    return;
                }
                //NewsRecyclerView newsRecyclerView = new NewsRecyclerView(articleLists);
//                articleLists = new ArrayList<NewsWireObjects>(newsWireResults.getResults().length);
                articleLists.clear();
                Collections.addAll(articleLists, newsWireResults.getResults());
                Log.i(TAG, articleLists.get(1).getTitle().toString());

                recycleAdapter.setData(articleLists);

            }

            @Override
            public void onFailure(Call<NewsWireResults> call, Throwable t) {

            }
        });
    }

    public void setViews(View v) {
        getNewsButton = (Button)v.findViewById(R.id.get_news);
        fragContainer = (FrameLayout) v.findViewById(R.id.frag_container);
        fab = (FloatingActionButton) v.findViewById(R.id.fab);
        articleFragment = new ArticleStory();
        articleListRecycleView = new ArticleListRecycleView();
        recyclerView = (RecyclerView) v.findViewById(R.id.recycle_view);

    }


    //This will need to setup with the RecycleView Click Listener
    public void setFragment() {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frag_container, articleListRecycleView);
        fragmentTransaction.commit();


    }

    public void onClicker() {
        getNewsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view, "Getting recent news", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                retrofitLatestNews();
            }
        });

    }


}
