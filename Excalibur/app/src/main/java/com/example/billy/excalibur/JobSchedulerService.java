package com.example.billy.excalibur;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

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
    private static final int NOTIFICATION_ID = 1;
    Context context;




    @Override
    public boolean onStartJob(JobParameters params) {
        context = getApplicationContext();
        retrofitLatestNews();
        return false;


    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

    public void retrofitLatestNews() {
        Log.i("JobSchedulerService", "API call was made!");
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
                setNotifications();
            }

            @Override
            public void onFailure(Call<NewsWireResults> call, Throwable t) {

            }
        });
    }

    /**
     * this method sets notifications
     * when system makes api call
     */
    private void setNotifications(){

        Intent intent = new Intent(context, MainActivity.class);

        // use System.currentTimeMillis() to have a unique ID for the pending intent
        PendingIntent pIntent = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), intent, 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setSmallIcon(R.drawable.ic_star_black_24dp);
        mBuilder.setContentTitle("Notification from Excalibur!");
        mBuilder.setContentText("New articles available!");
        mBuilder.setContentIntent(pIntent);
        mBuilder.setAutoCancel(true);
        mBuilder.setPriority(Notification.PRIORITY_HIGH);

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());

    }
}
