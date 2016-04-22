package com.example.billy.excalibur.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
public class ArticleListFragment extends Fragment {

    public final static String TAG = "ArticleRecycleView";

    private NewsRecyclerAdapter recycleAdapter;
    RecyclerView recyclerView;
    SearchAPI latestNewsService;
    Toolbar toolbar;
    public ArrayList<NewsWireObjects> breakingNewsLists;
    private String sections = "all";
    private String chooseMagazineSource = "all";
    protected SwipeRefreshLayout swipeContainer;
    private int numberOfArticles = 15;

    /**
     * Setter for Nav Drawer filtering API "sections" options
     */
    public void setSections(String sections) {
        this.sections = sections;
    }

    /**
     * sets magazine source between NY Times and Harold Mag
     * @param chooseMagazineSource
     */
    public void setChooseMagazineSource(String chooseMagazineSource) {
        this.chooseMagazineSource = chooseMagazineSource;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.recycleview_activity_fragment, container, false);

        setViews(v);
        breakingNewsLists = new ArrayList<>();
        recycleAdapter = new NewsRecyclerAdapter(breakingNewsLists);
        swipeContainer = (SwipeRefreshLayout)v.findViewById(R.id.swipeContainer);

        setPullRefresh();
        breakingNewsLists = new ArrayList<>();
        recycleAdapter = new NewsRecyclerAdapter(breakingNewsLists);

        retrofitLatestNews();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recycleAdapter.setOnItemClickListener(new NewsRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Log.i(TAG, String.valueOf(position));
                breakingNewsLists.get(position);

                Bundle article = new Bundle(); //will bundle the 5 fields of newsWireObjects in a string array
                String[] articleDetails = {breakingNewsLists.get(position).getSection(),
                        breakingNewsLists.get(position).getTitle(),
                        breakingNewsLists.get(position).getUrl(),
                        breakingNewsLists.get(position).getThumbnail_standard(),
                        breakingNewsLists.get(position).getAbstractResult()};
                article.putStringArray("article", articleDetails);

                Fragment articleStory = new ArticleStory();
                articleStory.setArguments(article);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frag_container, articleStory);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


        return v;
    }

    private void setPullRefresh(){
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                retrofitLatestNews();
                Log.i(TAG, "Refreshing list");
            }
        });

        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.darker_gray,
                android.R.color.white);

    }


    private void retrofitLatestNews() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.nytimes.com/svc/news/v3/content/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        latestNewsService = retrofit.create(SearchAPI.class);

        Call<NewsWireResults> call = latestNewsService.listNewsWireResults(chooseMagazineSource,
                sections, numberOfArticles);
        call.enqueue(new Callback<NewsWireResults>() {
            @Override
            public void onResponse(Call<NewsWireResults> call, Response<NewsWireResults> response) {
                NewsWireResults newsWireResults = response.body();
                swipeContainer.setRefreshing(false);

                if (newsWireResults == null) {
                    return;
                }

                Collections.addAll(breakingNewsLists, newsWireResults.getResults());

                if (recyclerView != null) {
                    recyclerView.setAdapter(recycleAdapter);
                    ///recycleAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Call<NewsWireResults> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void setViews(View v) {
        recyclerView = (RecyclerView) v.findViewById(R.id.recycle_view);

    }


}
