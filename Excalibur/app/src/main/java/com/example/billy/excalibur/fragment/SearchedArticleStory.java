package com.example.billy.excalibur.fragment;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
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
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.billy.excalibur.R;
import com.example.billy.excalibur.SaveForLater.ArticleSaveForLater;
import com.example.billy.excalibur.SaveForLater.SaveSQLiteHelper;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;

/**
 * Created by michaelmuccio on 4/21/16.
 */
public class SearchedArticleStory extends Fragment {
    ActionMenuItemView share;
    String[] articleDetails;
    View v;
    ShareButton fbSharebutton;

    private static final String TAG = "ArticleStory Fragment";
    private ProgressBar progress;
    private WebView articleWebView;
    private String htmlSaveForLater;
    private SQLiteDatabase db;
    private MenuItem saveLater;

    /**
     * user interface to callback for fragment
     */

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.article_activity_fragment, container, false);
        articleWebView = (WebView) v.findViewById(R.id.article_web_view);


        Bundle article = getArguments();

        articleDetails = article.getStringArray("searchedArticle");

        setFacebookButton();

        progress = (ProgressBar) v.findViewById(R.id.progress_bar);

        WebSettings webSettings = articleWebView.getSettings();
        articleWebView.setWebViewClient(new WebViewClientDemo()); //opens url in app, not in default browser
        webSettings.setJavaScriptEnabled(true); //turn js on for hacking and giving better ux
        articleWebView.addJavascriptInterface(new htmlJavaScriptInterface(), "HTMLOUT");

        articleWebView.loadUrl(articleDetails[1]);
        SaveSQLiteHelper mDbHelper = SaveSQLiteHelper.getInstance(getContext());
        db = mDbHelper.getWritableDatabase();

        Log.i(TAG, articleDetails[1]);

        setHasOptionsMenu(true);

        return v;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment, menu);
        saveLater = (MenuItem) menu.findItem(R.id.save_later);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.share) {
            Log.i(TAG, "Share button clicked!");

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, articleDetails[1]);
            intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Check out this site!");
            startActivity(Intent.createChooser(intent, "Share"));
            return true;
        } else if (id == R.id.save_later) {
            ArticleSaveForLater article = new ArticleSaveForLater(htmlSaveForLater, articleDetails[0], articleDetails[3], articleDetails[1], articleDetails[2]);
            insertIntoDbFromSearchArticle(article);
            return true;
        }

        return super.onOptionsItemSelected(item);

    }


    private class WebViewClientDemo extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            progress.setVisibility(View.VISIBLE);
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            articleWebView.loadUrl("javascript:window.HTMLOUT.showHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");
            progress.setVisibility(View.GONE);
            saveLater.setVisible(true);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progress.setVisibility(View.VISIBLE);
            saveLater.setVisible(false);
        }
    }

        public class htmlJavaScriptInterface {
            @JavascriptInterface
            @SuppressWarnings("unused")
            public void showHTML(String html) {
                htmlSaveForLater = html;
            }
        }

        public void setFacebookButton() {

            fbSharebutton = (ShareButton) v.findViewById(R.id.share_btn);
            ShareLinkContent content = new ShareLinkContent.Builder()
                    .setContentUrl(Uri.parse(articleDetails[1]))
                    .build();
            if (fbSharebutton != null) {
                fbSharebutton.setShareContent(content);
                Log.i(TAG, "Share button clicked!");
            }
        }

        private long insertIntoDbFromSearchArticle(ArticleSaveForLater article) {
            long newRowId = 0l;

            Cursor cursor;
            cursor = SaveSQLiteHelper.getInstance(getContext()).getAllSavedArticles();
            int isUniqueArticle = SaveSQLiteHelper.checkURLforDuplicate(article.getUrl(), cursor);
            cursor.close();

            if (isUniqueArticle == 0) {
                ContentValues values = new ContentValues();
                values.put(SaveSQLiteHelper.COL_HTML, article.getHtml());
                values.put(SaveSQLiteHelper.COL_TITLE, article.getTitle());
                values.put(SaveSQLiteHelper.COL_URL, article.getUrl());
                values.put(SaveSQLiteHelper.COL_SNIPPET, article.getSnippet());
                values.put(SaveSQLiteHelper.COL_IMAGE, article.getImage());
                values.put(SaveSQLiteHelper.COL_CODE, article.getCode());
                newRowId = db.insert(SaveSQLiteHelper.ARTICLES_TABLE_NAME, null, values);
                Toast.makeText(getContext(), "You saved " + ArticleSaveForLater.titleForToast(article.getTitle()), Toast.LENGTH_LONG).show();
            } else if (isUniqueArticle == -1) {
                Toast.makeText(getContext(), "Out of Space! Delete some old articles", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(), "You have already saved this article!", Toast.LENGTH_SHORT).show();
            }
            return newRowId;

        }
    }
