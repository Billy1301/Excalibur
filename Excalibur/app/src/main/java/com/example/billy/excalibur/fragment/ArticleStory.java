package com.example.billy.excalibur.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.billy.excalibur.R;

/**
 * Created by Billy on 4/16/16.
 */
public class ArticleStory extends Fragment {

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
        TextView aurthurName = (TextView)v.findViewById(R.id.article_aurthur);
        TextView articleInfo = (TextView)v.findViewById(R.id.article_story);





        return v;
    }
}
