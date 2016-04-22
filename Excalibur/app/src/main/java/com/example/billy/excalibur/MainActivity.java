package com.example.billy.excalibur;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.SearchView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.billy.excalibur.fragment.ArticleListFragment;
import com.example.billy.excalibur.fragment.ArticleStory;
import com.example.billy.excalibur.fragment.SavedArticleRecycleView;
import com.example.billy.excalibur.fragment.SearchArticlesFragment;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //region Private Variables
    private final static String TAG = "MainActivity";
    private FrameLayout fragContainer;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private ArticleStory articleFragment;
    private ArticleListFragment articleListFragment;
    private SearchArticlesFragment searchFrag;
    private SearchView searchView;
    private JobScheduler mJobScheduler;
    private TextView headerText;
    private ImageView headerImage;
    private View headerView;
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
        facebookInit();
        checkNetwork();
        setActionBarDrawer();
        setFragment();
        runAnimation();
        handleIntent(getIntent());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mJobScheduler !=null){
            mJobScheduler.cancelAll();
        }
    }

    /**
     * checking if network is available
     * when not on, it will display a notice and direct them to settings
     */
    public void checkNetwork(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo == null) {

            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    Intent intent = new Intent(Settings.ACTION_SETTINGS);
                    startActivity(intent);
                }
            }, 1600);

            Toast.makeText(MainActivity.this, R.string.network_check, Toast.LENGTH_LONG).show();
        }
    }

    private void setViews() {
        fragContainer = (FrameLayout) findViewById(R.id.frag_container);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        fragmentManager = getSupportFragmentManager();
        articleFragment = new ArticleStory();
        articleListFragment = new ArticleListFragment();
        headerView = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);
        headerText = (TextView) headerView.findViewById(R.id.nav_header_text_view);
        headerImage = (ImageView) headerView.findViewById(R.id.imageView);
    }

    private void setFragment() {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frag_container, articleListFragment);
        fragmentTransaction.commit();
    }

    private void setActionBarDrawer() {
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

    private void facebookInit(){
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }
    /**
     * This is for the search option
     * @param intent
     */

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            searchFrag = new SearchArticlesFragment();
            String query = intent.getStringExtra(SearchManager.QUERY);
            searchFrag.setQuery(query);

            Toast.makeText(MainActivity.this,getString(R.string.searching_for_toast)+ query, Toast.LENGTH_SHORT).show();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frag_container, searchFrag);
            fragmentTransaction.commit();

            toolbar.setTitle(R.string.toolbar_search_title);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        ArticleListFragment topicFrag = new ArticleListFragment();
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
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
            case R.id.nav_notification_settings:
                if(item.getTitle().toString().equals(getString(R.string.stopNotification))) {
                    mJobScheduler.cancelAll();
                    item.setTitle(getString(R.string.startNotification));
                    Toast.makeText(MainActivity.this, R.string.stopNotification, Toast.LENGTH_SHORT).show();
                }
                else {
                    callJobScheduler();
                    item.setTitle(getString(R.string.stopNotification));
                    Toast.makeText(MainActivity.this, R.string.startNotification, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.nav_save:
                SavedArticleRecycleView savedFrag = new SavedArticleRecycleView();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frag_container, savedFrag);
                fragmentTransaction.commit();
                toolbar.setTitle(getString(R.string.save_for_later));
                break;

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * this method makes API calls at a pre-set time
     *
     */
    private void callJobScheduler() {

        mJobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        JobInfo.Builder builder = new JobInfo.Builder(1, new ComponentName(getPackageName(),
                JobSchedulerService.class.getName()));
        builder.setPeriodic(600000);

        if (mJobScheduler.schedule(builder.build()) <= 0) {
        }
    }

    /**
     * sets animation to textView
     * of menu header
     */
    private void runAnimation() {
        headerImage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
               // Log.i(TAG, "Image is clicked!");
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.complex_animation);
                animation.reset();
                headerText.clearAnimation();
                headerText.startAnimation(animation);
                
                return false;
            }
        });

    }


}
