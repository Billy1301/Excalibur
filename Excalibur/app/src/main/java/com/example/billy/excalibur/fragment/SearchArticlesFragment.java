package com.example.billy.excalibur.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.billy.excalibur.Adaptors.SearchArticleAdapter;
import com.example.billy.excalibur.NyTimesAPIService.ArticleSearchAPI.Doc;
import com.example.billy.excalibur.NyTimesAPIService.ArticleSearchAPI.ArticleSearch;
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
 * Created by michaelmuccio on 4/20/16.
 */
public class SearchArticlesFragment extends Fragment {
    public final static String TAG = "ArticleRecycleView";

    SearchArticleAdapter searchArticleAdapter;
    RecyclerView searchRecyclerView;
    private SearchAPI articleSearchResponse;
    public ArrayList<Doc> articleSearchList;
    private String query = "";

    public void setQuery(String query) {
        this.query = query;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.search_recycler_activity_fragment, container, false);

        setViews(v);
        articleSearchList = new ArrayList<>();
        searchArticleAdapter = new SearchArticleAdapter(articleSearchList);

        searchBar();
        searchRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        searchArticleAdapter.setOnItemClickListener(new SearchArticleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.i(TAG, String.valueOf(position));
                articleSearchList.get(position);
                Bundle article = new Bundle(); //will bundle the 5 fields of articleSearchObjects in a string array
                String[] articleDetails = {articleSearchList.get(position).getSection_name(),
                        articleSearchList.get(position).getHeadline().getMain(),
                        articleSearchList.get(position).getWeb_url(),
                        articleSearchList.get(position).getMultimedia()[0].getUrl(),
                        articleSearchList.get(position).getLead_paragraph()};
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
        Call<ArticleSearch> call = articleSearchResponse.listArticleSearchDocs(query);
        call.enqueue(new Callback<ArticleSearch>() {
            @Override
            public void onResponse(Call<ArticleSearch> call, Response<ArticleSearch> response) {
                ArticleSearch articleSearchDocs = response.body();
                Log.i(TAG, "Searched Article: " + articleSearchDocs);
                if(articleSearchDocs == null){
                    return;
                }

                if (articleSearchDocs == null) {
                    return;
                }

                Collections.addAll(articleSearchList, articleSearchDocs.getResponse().getDocs());
                Log.i(TAG, "Searched Article: " + articleSearchList);

                if (searchRecyclerView != null) {
                    searchRecyclerView.setAdapter(searchArticleAdapter);
                }
            }

            @Override
            public void onFailure(Call<ArticleSearch> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void setViews(View v) {
        searchRecyclerView = (RecyclerView) v.findViewById(R.id.search_recycle_view);

    }


}
