package com.example.billy.excalibur;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.billy.excalibur.Adaptors.NewsRecyclerAdapter;
import com.example.billy.excalibur.NyTimesAPIService.NewsWireObjects;
import com.example.billy.excalibur.NyTimesAPIService.NewsWireResults;
import com.example.billy.excalibur.NyTimesAPIService.SearchAPI;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Mikhail on 4/20/16.
 */
public class JobSchedulerService extends JobService {

    NewsRecyclerAdapter recycleAdapter;
    RecyclerView recyclerView;
    SearchAPI latestNewsService;
    public ArrayList<NewsWireObjects> articleLists;
    public String sections = "all";
    public String chooseMagazineSource = "all";


    @Override
    public boolean onStartJob(JobParameters params) {

        retrofitLatestNews();
        return true;  //??????


    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

    public void retrofitLatestNews() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.nytimes.com/svc/news/v3/content/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        latestNewsService = retrofit.create(SearchAPI.class);

        Call<NewsWireResults> call = latestNewsService.listNewsWireResults(chooseMagazineSource,
                sections, 10);
        call.enqueue(new Callback<NewsWireResults>() {
            @Override
            public void onResponse(Call<NewsWireResults> call, Response<NewsWireResults> response) {
                NewsWireResults newsWireResults = response.body();

                if (newsWireResults == null) {
                    return;
                }

                Collections.addAll(articleLists, newsWireResults.getResults());

                if (recyclerView != null) {
                    recyclerView.setAdapter(recycleAdapter);
                    ///recycleAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Call<NewsWireResults> call, Throwable t) {

            }
        });
    }
}
