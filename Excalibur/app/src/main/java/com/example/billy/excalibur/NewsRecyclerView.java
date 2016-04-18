package com.example.billy.excalibur;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.billy.excalibur.NyTimesAPIService.NewsWireObjects;
import com.example.billy.excalibur.NyTimesAPIService.NewsWireResults;

import java.util.List;

/**
 * Created by Mikhail on 4/17/16.
 */
public class NewsRecyclerView extends RecyclerView.Adapter<NewsRecyclerView.NewsRecyclerViewHolder> {

    List<NewsWireObjects> data;

    TextView headline;
    ImageView imageIcon;
    TextView articleBody;

    public NewsRecyclerView(List<NewsWireObjects> data) {
        this.data = data;
    }


    public class NewsRecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView headline;

        public NewsRecyclerViewHolder(View itemView) {
            super(itemView);

            headline = (TextView) itemView.findViewById(R.id.headline);
        }

    }


    @Override
    public NewsRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recyclerview_layout, parent, false);
        NewsRecyclerViewHolder vh = new NewsRecyclerViewHolder(view);

        headline = (TextView) view.findViewById(R.id.article_headline_title);
        imageIcon = (ImageView) view.findViewById(R.id.cardView_image);
        articleBody = (TextView) view.findViewById(R.id.article_info_cardview);


        return vh;
    }

    @Override
    public void onBindViewHolder(NewsRecyclerViewHolder holder, int position) {
        //TODO: Set our textView to our data - News object

        holder.headline.setText(data.get(position).getSection());

//        headline.setText("Headline");
//        imageIcon.setImageResource(R.drawable.ic_menu_gallery);
//        articleBody.setText("Article story into here");

    }

    @Override
    public int getItemCount() {
        return data.size();

    }
}

