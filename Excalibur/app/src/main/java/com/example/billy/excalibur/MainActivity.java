package com.example.billy.excalibur;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.billy.excalibur.NyTimesAPIService.ArticleSearchObjects;
import com.example.billy.excalibur.NyTimesAPIService.ArticleSearchResponse;


import com.example.billy.excalibur.Adaptors.NewsRecyclerAdapter;
import com.example.billy.excalibur.NyTimesAPIService.NewsWireObjects;
import com.example.billy.excalibur.NyTimesAPIService.SearchAPI;
import com.example.billy.excalibur.fragment.ArticleListFragment;
import com.example.billy.excalibur.fragment.ArticleStory;
import com.example.billy.excalibur.fragment.SearchArticlesFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


import java.util.ArrayList;
import java.util.Collection;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private final static String TAG = "MainActivity";
    public final static String SEARCH_KEY = "searchKey";
    RecyclerView recyclerView;
    NewsRecyclerAdapter recycleAdapter;
    FrameLayout fragContainer;
    NavigationView navigationView;
    DrawerLayout drawer;
    Toolbar toolbar;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    com.example.billy.excalibur.fragment.ArticleStory articleFragment;
    ArticleListFragment articleListFragment;
    public static ArrayList<NewsWireObjects> articleLists;
    ActionMenuItemView share;
    SearchView searchView;
    SearchArticlesFragment searchArticlesFragment;

    private String BREAKING_NEWS = "all";
    private String BUSINESS_DAY = "business day";
    private String WORLD = "world";
    private String US = "u.s.";
    private String TECHNOLOGY = "technology";
    private String SCIENCE = "science";
    private String NY_TIMES = "nyt";
    private String ARTS = "arts";
    private String HEALTH = "health";
    private String SPORTS = "sports";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setViews();
        setActionBarDrawer();
        navigationView.setNavigationItemSelectedListener(this);
        articleLists = new ArrayList<>();
        setFragment();
        handleIntent(getIntent());


    }

    public void setViews() {
        fragContainer = (FrameLayout) findViewById(R.id.frag_container);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        fragmentManager = getSupportFragmentManager();
        articleFragment = new ArticleStory();
        articleListFragment = new ArticleListFragment();
        searchArticlesFragment = new SearchArticlesFragment();
    }

    public void setFragment() {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frag_container, articleListFragment);
        fragmentTransaction.commit();
    }

    public void setActionBarDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
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

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Bundle searchArticleBundle = new Bundle();
            searchArticleBundle.putString(SEARCH_KEY, query);
            searchArticlesFragment.setArguments(searchArticleBundle);

            Toast.makeText(MainActivity.this,"Searching for "+ query, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        share = (ActionMenuItemView) toolbar.findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Share button clicked!");


            }
        });


        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

//    private void searchBar(String query){
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://api.nytimes.com/svc/search/v2/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        articleSearchResponse = retrofit.create(SearchAPI.class);
//        Call<ArticleSearchResponse> call = articleSearchResponse.listArticleSearchDocs(query);
//        call.enqueue(new Callback<ArticleSearchResponse>() {
//            @Override
//            public void onResponse(Call<ArticleSearchResponse> call, Response<ArticleSearchResponse> response) {
//                ArticleSearchResponse articleSearchDocs = response.body();
//                if(articleSearchDocs == null){
//                    return;
//                }
//                ArrayList<ArticleSearchObjects> articleSearch = new ArrayList<>();
//                articleSearch.addAll((Collection<? extends ArticleSearchObjects>) articleSearchDocs.getResponse());
//                Log.i(TAG, "Searched Articles: " + articleSearch);
//
//                if(recyclerView != null) {
//                    recyclerView.setAdapter(recycleAdapter);
//                    recycleAdapter.notifyDataSetChanged();
//                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
//                }
//
//                searchFrag = new ArticleListFragment();
//                searchFrag.setViews(recyclerView);
//                fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.frag_container, searchFrag);
//                fragmentTransaction.commit();
//
//            }
//
//            @Override
//            public void onFailure(Call<ArticleSearchResponse> call, Throwable t) {
//                t.printStackTrace();
//            }
//        });
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        ArticleListFragment topicFrag = new ArticleListFragment();
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id){
            case R.id.nav_breakingNews:
                topicFrag.setSections(BREAKING_NEWS);
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frag_container, topicFrag);
                fragmentTransaction.commit();
                toolbar.setTitle(getString(R.string.breakingNews));
                break;
            case R.id.nav_world:
                topicFrag.setSections(WORLD);
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frag_container, topicFrag);
                fragmentTransaction.commit();
                toolbar.setTitle(getString(R.string.world));
                break;
            case R.id.nav_us:
                topicFrag.setSections(US);
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frag_container, topicFrag);
                fragmentTransaction.commit();
                toolbar.setTitle(getString(R.string.u_s));
                break;
            case R.id.nav_technology:
                topicFrag.setSections(TECHNOLOGY);
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frag_container, topicFrag);
                fragmentTransaction.commit();
                toolbar.setTitle(getString(R.string.technology));
                break;
            case R.id.nav_health:
                topicFrag.setSections(HEALTH);
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frag_container, topicFrag);
                fragmentTransaction.commit();
                toolbar.setTitle(getString(R.string.health));
                break;
            case R.id.nav_ny_times:
                Log.i(TAG, "Nav gallery clicked");
                topicFrag.setChooseMagazineSource(NY_TIMES);
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frag_container, topicFrag);
                fragmentTransaction.commit();
                toolbar.setTitle(getString(R.string.n_y));
                break;
            case R.id.nav_business:
                topicFrag.setSections(BUSINESS_DAY);
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frag_container, topicFrag);
                fragmentTransaction.commit();
                toolbar.setTitle(getString(R.string.businessDay));
                break;
            case R.id.nav_sports:
                topicFrag.setSections(SPORTS);
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frag_container, topicFrag);
                fragmentTransaction.commit();
                toolbar.setTitle(getString(R.string.sports));
                break;
            case R.id.nav_arts:
                topicFrag.setSections(ARTS);
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frag_container, topicFrag);
                fragmentTransaction.commit();
                toolbar.setTitle(getString(R.string.arts));
                break;
            case R.id.nav_science:
                topicFrag.setSections(SCIENCE);
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frag_container, topicFrag);
                fragmentTransaction.commit();
                toolbar.setTitle(getString(R.string.science));
                break;
            case R.id.nav_share:
                break;
            case R.id.nav_save:
                break;

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
