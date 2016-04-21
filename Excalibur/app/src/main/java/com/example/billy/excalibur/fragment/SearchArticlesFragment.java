package com.example.billy.excalibur.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.billy.excalibur.Adaptors.NewsRecyclerAdapter;
import com.example.billy.excalibur.Adaptors.SearchArticleAdapter;
import com.example.billy.excalibur.MainActivity;
import com.example.billy.excalibur.NyTimesAPIService.ArticleSearchObjects;
import com.example.billy.excalibur.NyTimesAPIService.ArticleSearchResponse;
import com.example.billy.excalibur.NyTimesAPIService.NewsWireObjects;
import com.example.billy.excalibur.NyTimesAPIService.NewsWireResults;
import com.example.billy.excalibur.NyTimesAPIService.SearchAPI;
import com.example.billy.excalibur.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by michaelmuccio on 4/20/16.
 */
public class SearchArticlesFragment extends Fragment {
    public final static String TAG = "ArticleRecycleView";

    private SearchArticleAdapter recycleAdapter;
    RecyclerView recyclerView;
    public ArrayList<ArticleSearchObjects> articleSearch;
    private SearchAPI articleSearchResponse;
    private String query = "Steph Curry";

    public void setQuery(String query) {
        this.query = query;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.recycleview_activity_fragment, container, false);

        setViews(v);
        articleSearch = new ArrayList<>();
        recycleAdapter = new SearchArticleAdapter(articleSearch);

        searchBar();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recycleAdapter.setOnItemClickListener(new NewsRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.i(TAG, String.valueOf(position));
                articleSearch.get(position);
                Bundle article = new Bundle(); //will bundle the 5 fields of articleSearchObjects in a string array
                String[] articleDetails = {articleSearch.get(position).getSection_name(),
                        articleSearch.get(position).getHeadline(),
                        articleSearch.get(position).getWeb_url(),
                        articleSearch.get(position).getMultimedia(),
                        articleSearch.get(position).getSnippet()};
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

    private void searchBar(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.nytimes.com/svc/search/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        articleSearchResponse = retrofit.create(SearchAPI.class);
        Call<ArticleSearchResponse> call = articleSearchResponse.listArticleSearchDocs(query);
        call.enqueue(new Callback<ArticleSearchResponse>() {
            @Override
            public void onResponse(Call<ArticleSearchResponse> call, Response<ArticleSearchResponse> response) {
                ArticleSearchResponse articleSearchDocs = response.body();
                if(articleSearchDocs == null){
                    return;
                }

                if (articleSearchDocs == null) {
                    return;
                }

                Collections.addAll(articleSearch, articleSearchDocs.getResponse().getDocs());

                if (recyclerView != null) {
                    recyclerView.setAdapter(recycleAdapter);
                }
            }

            @Override
            public void onFailure(Call<ArticleSearchResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void setViews(View v) {
        recyclerView = (RecyclerView) v.findViewById(R.id.search_recycle_view);

    }


}
