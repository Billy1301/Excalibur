package com.example.billy.excalibur;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.billy.excalibur.Adaptors.NewsRecyclerAdapter;
import com.example.billy.excalibur.NyTimesAPIService.NewsWireObjects;
import com.example.billy.excalibur.NyTimesAPIService.NewsWireResults;
import com.example.billy.excalibur.NyTimesAPIService.SearchAPI;
import com.example.billy.excalibur.fragment.ArticleListRecycleView;
import com.example.billy.excalibur.fragment.ArticleStory;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.Collections;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public final static String TAG = "MainActivity";
    NewsRecyclerAdapter recycleAdapter;
    RecyclerView recyclerView;
    SearchAPI latestNewsService;
    FrameLayout fragContainer;
    NavigationView navigationView;
    FloatingActionButton fab;
    DrawerLayout drawer;
    Toolbar toolbar;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    ArticleStory articleFragment;
    ArticleListRecycleView articleListRecycleView;
    public static ArrayList<NewsWireObjects> articleLists;
    private String BREAKING_NEWS = "breaking news";
    private String SCIENCE = "science";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setViews();
        //setFAB();
        setActionBarDrawer();
        navigationView.setNavigationItemSelectedListener(this);

        //retrofitLatestNews();
        articleLists = new ArrayList<>();
        //PreloadTenArticles.preloadArticles();
        setFragment();

/*




        if (recyclerView != null) {
            recycleAdapter = new NewsRecyclerAdapter(articleLists);
            recyclerView.setAdapter(recycleAdapter);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
*/


    }

    private void retrofitLatestNews() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.nytimes.com/svc/news/v3/content/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        latestNewsService = retrofit.create(SearchAPI.class);
        Call<NewsWireResults> call = latestNewsService.listNewsWireResults("us", 5);
        call.enqueue(new Callback<NewsWireResults>() {
            @Override
            public void onResponse(Call<NewsWireResults> call, Response<NewsWireResults> response) {
                NewsWireResults newsWireResults = response.body();

                if (newsWireResults == null) {
                    return;
                }
                NewsRecyclerAdapter newsRecyclerAdaptor = new NewsRecyclerAdapter(articleLists);
                articleLists = new ArrayList<NewsWireObjects>(newsWireResults.getResults().length);
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

    public void setViews() {
        fragContainer = (FrameLayout) findViewById(R.id.frag_container);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        fragmentManager = getSupportFragmentManager();
        articleFragment = new ArticleStory();
        articleListRecycleView = new ArticleListRecycleView();
        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);

    }


    //This will need to setup with the RecycleView Click Listener
    public void setFragment() {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frag_container, articleListRecycleView);
        fragmentTransaction.commit();


    }


    public void setActionBarDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    public void setFAB() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                retrofitLatestNews();
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id){
            case R.id.nav_ny_times:
                Log.i(TAG, "Nav gallery clicked");
                break;
            case R.id.nav_business:
                break;
            case R.id.nav_sports:
                break;
            case R.id.nav_arts:
                break;
            case R.id.nav_harold_magazine:
                break;
            case R.id.nav_science:
                ArticleListRecycleView scienceFrag = new ArticleListRecycleView();
                scienceFrag
                break;
            case R.id.nav_share:
                break;
            case R.id.nav_save:
                break;
            default: id = R.id.nav_breakingNews;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
