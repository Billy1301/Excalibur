package com.example.billy.excalibur.fragment;

import android.database.Cursor;
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
import android.widget.TextView;

import com.example.billy.excalibur.Adaptors.NewsRecyclerAdapter;
import com.example.billy.excalibur.Adaptors.SavedRecyclerAdapter;
import com.example.billy.excalibur.NyTimesAPIService.NewsWireObjects;
import com.example.billy.excalibur.NyTimesAPIService.NewsWireResults;
import com.example.billy.excalibur.NyTimesAPIService.SearchAPI;
import com.example.billy.excalibur.R;
import com.example.billy.excalibur.SaveForLater.ArticleSaveForLater;
import com.example.billy.excalibur.SaveForLater.SaveSQLiteHelper;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by petermartinez on 4/21/16.
 */
public class SavedArticleRecycleView extends Fragment {
    public final static int savedArticleLimit = 15;
    public ArrayList<ArticleSaveForLater> articleLists;

    private SavedRecyclerAdapter recycleAdapter;
    private RecyclerView recyclerView;
    private TextView storiesCount;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.saved_recycleview_fragment, container, false);

        setViews(v);
        articleLists = new ArrayList<>();
        recycleAdapter = new SavedRecyclerAdapter(articleLists);

        getSavedArticles();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recycleAdapter.setOnItemClickListener(new SavedRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                storiesCount.setVisibility(View.GONE);
                articleLists.get(position);
                Bundle article = new Bundle();
                String[] articleDetails = {articleLists.get(position).getTitle(),
                        articleLists.get(position).getUrl(),
                        articleLists.get(position).getImage(),
                        articleLists.get(position).getSnippet(),
                        String.valueOf(articleLists.get(position).getCode()),
                        String.valueOf(articleLists.get(position).getId())};
                article.putStringArray("article", articleDetails);

                Fragment savedArticleStory = new SavedArticleStory();
                savedArticleStory.setArguments(article);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frag_container, savedArticleStory);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return v;
    }

    private void getSavedArticles() {
        Cursor cursor;
        cursor = SaveSQLiteHelper.getInstance(getContext()).getAllSavedArticles();
        dumpCursorToArray(cursor);
        cursor.close();

        if (recyclerView != null) {
            recyclerView.setAdapter(recycleAdapter);
        }

    }

    public void setViews(View v) {
        recyclerView = (RecyclerView) v.findViewById(R.id.saved_recycle_view);
        storiesCount = (TextView) v.findViewById(R.id.stories_count);
        storiesCount.setVisibility(View.VISIBLE);

    }

    public void dumpCursorToArray(Cursor cursor) {
        articleLists.clear();
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            ArticleSaveForLater article = new ArticleSaveForLater();
            Log.d("db output", cursor.getString(0));
//            for(int i = 0; i < colCount; i++){
//                Log.i("DB", colNames[i] + " : " + cursor.getString(i));
//            }

            article.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(SaveSQLiteHelper.COL_ID))));
            article.setTitle((cursor.getString(cursor.getColumnIndex(SaveSQLiteHelper.COL_TITLE))));
            article.setSnippet((cursor.getString(cursor.getColumnIndex(SaveSQLiteHelper.COL_SNIPPET))));
            article.setUrl((cursor.getString(cursor.getColumnIndex(SaveSQLiteHelper.COL_URL))));
            article.setImage((cursor.getString(cursor.getColumnIndex(SaveSQLiteHelper.COL_IMAGE))));
            article.setCode(Long.parseLong(cursor.getString(cursor.getColumnIndex(SaveSQLiteHelper.COL_CODE))));


            cursor.moveToNext();
            articleLists.add(article);
            recycleAdapter.notifyDataSetChanged();
        }

        storiesCount.setText("You have " + articleLists.size() + "/" + String.valueOf(savedArticleLimit) + " articles saved");
//        if(articleLists.size() < 1){
//            storiesCount.setVisibility(View.VISIBLE);
//        } else {
//            storiesCount.setVisibility(View.GONE);
//        }

    }
}
