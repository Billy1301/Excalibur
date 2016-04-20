package com.example.billy.excalibur.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.view.menu.ActionMenuItemView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.billy.excalibur.R;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;
import com.squareup.picasso.Picasso;

/**
 * Created by Billy on 4/16/16.
 */
public class ArticleStory extends Fragment {

    ActionMenuItemView share;
    String[] articleDetails;


    private static final String TAG = "ArticleStory Fragment";

    /**
     * user interface to callback for fragment
     */

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.article_activity_fragment, container, false);
        WebView articleWebView = (WebView) v.findViewById(R.id.article_web_view);

        Bundle article = getArguments();

        articleDetails = article.getStringArray("article");

        WebSettings webSettings = articleWebView.getSettings();
        webSettings.setJavaScriptEnabled(true); //turn js on for hacking and giving better ux
        articleWebView.setWebViewClient(new WebViewClient()); //opens url in app, not in default browser
        articleWebView.loadUrl(articleDetails[2]);

        Log.i(TAG, articleDetails[0]);
        Log.i(TAG, articleDetails[1]);
        Log.i(TAG, articleDetails[2]);
        Log.i(TAG, articleDetails[3]);
        Log.i(TAG, articleDetails[4]);


        setHasOptionsMenu(true);

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.share){
            Log.i(TAG, "Share button clicked!");

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, articleDetails[2]);
            intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Check out this site!");
            startActivity(Intent.createChooser(intent, "Share"));
            return true;
        }
        
        return super.onOptionsItemSelected(item);

    }
}
