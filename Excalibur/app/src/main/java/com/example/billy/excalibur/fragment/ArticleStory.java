package com.example.billy.excalibur.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.billy.excalibur.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Billy on 4/16/16.
 */
public class ArticleStory extends Fragment {

    private static final String TAG = "ArticleStory Fragment";

    /**
     *  user interface to callback for fragment
     *
     */

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.article_activity_fragment, container, false);

        ImageView image = (ImageView)v.findViewById(R.id.article_image_view);
        TextView articleTitle = (TextView)v.findViewById(R.id.article_headline_title);
        TextView authorName = (TextView)v.findViewById(R.id.article_author);
        TextView articleInfo = (TextView)v.findViewById(R.id.article_story);

        Bundle article = getArguments();

        String[] articleDetails = article.getStringArray("article");

        Log.i(TAG, articleDetails[0]);
        Log.i(TAG, articleDetails[1]);
        Log.i(TAG, articleDetails[2]);
        Log.i(TAG, articleDetails[3]);
        Log.i(TAG, articleDetails[4]);

        articleTitle.setText(articleDetails[1]);
        Picasso.with(getContext()) //we need to add a check for picture object, not all a
                .load(articleDetails[3])
                .placeholder(R.drawable.nyt_icon)
                .resize(200, 200)
                .centerCrop()
                .into(image);






        return v;
    }
}
