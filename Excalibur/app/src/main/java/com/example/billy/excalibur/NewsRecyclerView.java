package com.example.billy.excalibur;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.billy.excalibur.NyTimesAPIService.NewsWireObjects;

import com.example.billy.excalibur.NyTimesAPIService.SearchAPI;
import com.squareup.picasso.Picasso;



import java.util.ArrayList;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Mikhail on 4/17/16.
 */
public class NewsRecyclerView extends RecyclerView.Adapter<NewsRecyclerView.NewsRecyclerViewHolder> {

    ArrayList<NewsWireObjects> data;
    Context context;

    SearchAPI latestNewsService;
    private String TAG = "RecyclerViewAdaptor";
    //TODO: create constructor for News object


    TextView headline;
    ImageView imageIcon;
    TextView articleBody;

    public NewsRecyclerView(ArrayList<NewsWireObjects> data) {
        this.data = data;
    }



    public class NewsRecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView headline;

        public NewsRecyclerViewHolder(View itemView) {
            super(itemView);

            headline = (TextView) itemView.findViewById(R.id.headline);
        }

    }

    public void setData(ArrayList<NewsWireObjects> data) {
        this.data = data;
    }

    @Override
    public NewsRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
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


        holder.headline.setText(data.get(position).getTitle());
        String imageURI = data.get(position).getThumbnail_standard();
        if(imageURI.isEmpty()){
            imageURI = "R.drawable.nyt_icon";
        }

        Picasso.with(context) //we need to add a check for picture object, not all a
                .load(imageURI)
                .placeholder(R.drawable.nyt_icon)
                .resize(100, 100)
                .centerCrop()
                .into(imageIcon);
       articleBody.setText(data.get(position).getAbstractResult());

    }

    @Override
    public int getItemCount() {
        return data.size();

    }
}

