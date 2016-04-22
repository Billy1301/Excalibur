package com.example.billy.excalibur.Adaptors;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.billy.excalibur.NyTimesAPIService.NewsWireObjects;
import com.example.billy.excalibur.R;
import com.example.billy.excalibur.SaveForLater.ArticleSaveForLater;
import com.squareup.picasso.Picasso;
import com.example.billy.excalibur.NyTimesAPIService.SearchAPI;

import java.util.ArrayList;

/**
 * Created by Mikhail on 4/17/16.
 */
public class SavedRecyclerAdapter extends RecyclerView.Adapter<SavedRecyclerAdapter.SavedRecyclerViewHolder> {

    ArrayList<ArticleSaveForLater> data;
    Context context;
    private static OnItemClickListener listener;

    public SavedRecyclerAdapter(ArrayList<ArticleSaveForLater> data) {
        this.data = data;
    }

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public class SavedRecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView headline;
        ImageView imageIcon;
        TextView articleAbstract;
        TextView ago;
        ArticleSaveForLater articlesSaved;

        public SavedRecyclerViewHolder(final View itemView) {
            super(itemView);

            headline = (TextView) itemView.findViewById(R.id.saved_headline);
            imageIcon = (ImageView)itemView.findViewById(R.id.saved_cardView_image);
            articleAbstract = (TextView)itemView.findViewById(R.id.saved_article_info_cardview);
            ago = (TextView)itemView.findViewById(R.id.saved_ago);
            articlesSaved = new ArticleSaveForLater();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.onItemClick(itemView, getLayoutPosition());
                }
            });

        }

    }

    public void setData(ArrayList<ArticleSaveForLater> data) {
        this.data = data;
    }

    @Override
    public SavedRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.save_recyclerview_layout, parent, false);
        SavedRecyclerViewHolder vh = new SavedRecyclerViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(SavedRecyclerViewHolder holder, int position) {
        long timeStamp = System.currentTimeMillis();
        holder.headline.setText(data.get(position).getTitle());
        holder.articleAbstract.setText(data.get(position).getSnippet());
        holder.ago.setText("saved " + NewsRecyclerAdapter.getBiggestUnitTimeElapsed(data.get(position).getCode(), timeStamp) + " ago");


        String imageURI = data.get(position).getImage();
        if(imageURI.isEmpty()){
            imageURI = "R.drawable.nyt_icon";
        }

        Picasso.with(context)
                .load(imageURI)
                .placeholder(R.drawable.nyt_icon)
                .resize(160, 160)
                .centerCrop()
                .into(holder.imageIcon);
        holder.articleAbstract.setText(data.get(position).getSnippet());

    }

    @Override
    public int getItemCount() {
        return data.size();

    }
}

