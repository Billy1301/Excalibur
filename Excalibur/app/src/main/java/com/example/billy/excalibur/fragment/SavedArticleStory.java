package com.example.billy.excalibur.fragment;

import android.content.ClipData;
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
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.billy.excalibur.R;
import com.example.billy.excalibur.SaveForLater.ArticleSaveForLater;
import com.example.billy.excalibur.SaveForLater.SaveSQLiteHelper;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;

/**
 * Created by petermartinez on 4/21/16.
 */
public class SavedArticleStory extends Fragment {

    //region private variables
    private String[] articleDetails;
    private View v;
    private ShareButton fbSharebutton;
    private static final String TAG = "Saved Article Fragment";
    private ProgressBar progress;
    private WebView articleWebView;
    private ArticleSaveForLater articleSaved;
    private MenuItem deleteButton;
    private SQLiteDatabase db;
    //endregion

    /**
     * user interface to callback for fragment
     */

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.article_activity_fragment, container, false);
        articleWebView = (WebView) v.findViewById(R.id.article_web_view);
        progress = (ProgressBar) v.findViewById(R.id.progress_bar);
        fbSharebutton = (ShareButton)v.findViewById(R.id.share_btn);
        fbSharebutton.setVisibility(View.GONE);

        Bundle article = getArguments();
        articleDetails = article.getStringArray("article");
        SaveSQLiteHelper mDbHelper = SaveSQLiteHelper.getInstance(getContext());
        db = mDbHelper.getWritableDatabase();

        WebSettings webSettings = articleWebView.getSettings();
        articleWebView.setWebViewClient(new WebViewClientDemo()); //opens url in app, not in default browser
        webSettings.setJavaScriptEnabled(true); //turn js on for hacking and giving better ux

        Cursor cursor;
        cursor = SaveSQLiteHelper.getInstance(getContext()).getArticleHtml(articleDetails[5]);
        dumpCursorToSavedArticle(cursor);
        cursor.close();

        articleWebView.loadDataWithBaseURL("", articleSaved.getHtml(), "text/html", "UTF-8", "");
        setHasOptionsMenu(true);
        return v;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment, menu);
        deleteButton = (MenuItem) menu.findItem(R.id.save_later);
        deleteButton.setIcon(android.R.drawable.ic_menu_close_clear_cancel);
        deleteButton.setVisible(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.share) {
            Log.i(TAG, "Share button clicked!");

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, articleDetails[0]);
            intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Check out this site!");
            startActivity(Intent.createChooser(intent, "Share"));
            return true;
        }else if (id == R.id.save_later) {
            deleteSavedStoryById(String.valueOf(articleSaved.getId()));
            Toast.makeText(getContext(), "You deleted " + ArticleSaveForLater.titleForToast(articleSaved.getTitle()), Toast.LENGTH_LONG).show();
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
            progress.setVisibility(View.GONE);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progress.setVisibility(View.VISIBLE);
        }
    }

    public void dumpCursorToSavedArticle(Cursor cursor) {
        cursor.moveToFirst();
        articleSaved = new ArticleSaveForLater();
        articleSaved.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(SaveSQLiteHelper.COL_ID))));
        articleSaved.setHtml((cursor.getString(cursor.getColumnIndex(SaveSQLiteHelper.COL_HTML))));
        articleSaved.setTitle((cursor.getString(cursor.getColumnIndex(SaveSQLiteHelper.COL_TITLE))));
        articleSaved.setSnippet((cursor.getString(cursor.getColumnIndex(SaveSQLiteHelper.COL_SNIPPET))));
        articleSaved.setUrl((cursor.getString(cursor.getColumnIndex(SaveSQLiteHelper.COL_URL))));
        articleSaved.setImage((cursor.getString(cursor.getColumnIndex(SaveSQLiteHelper.COL_IMAGE))));
        articleSaved.setCode(Long.parseLong(cursor.getString(cursor.getColumnIndex(SaveSQLiteHelper.COL_CODE))));
}

    public void deleteSavedStoryById(String Id){
        String[] query = {Id};
        db.delete(SaveSQLiteHelper.ARTICLES_TABLE_NAME, SaveSQLiteHelper.COL_ID + " = ?", query);
    }
}
