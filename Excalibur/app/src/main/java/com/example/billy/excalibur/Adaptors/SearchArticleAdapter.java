package com.example.billy.excalibur.Adaptors;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.billy.excalibur.NyTimesAPIService.ArticleSearchAPI.ArticleSearch;
import com.example.billy.excalibur.NyTimesAPIService.ArticleSearchAPI.Doc;
import com.example.billy.excalibur.NyTimesAPIService.ArticleSearchAPI.Multimedia;
import com.example.billy.excalibur.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by michaelmuccio on 4/21/16.
 */
public class SearchArticleAdapter extends RecyclerView.Adapter<SearchArticleAdapter.NewsRecyclerViewHolder> {
    ArrayList<Doc> data;
    Context context;
    private static OnItemClickListener listener;

    public SearchArticleAdapter(ArrayList<Doc> data) {
        this.data = data;
    }

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public class NewsRecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView headline;
        ImageView imageIcon;
        TextView articleAbstract;
        TextView ago;
        Doc doc;

        public NewsRecyclerViewHolder(final View itemView) {
            super(itemView);

            headline = (TextView) itemView.findViewById(R.id.headline);
            imageIcon = (ImageView)itemView.findViewById(R.id.cardView_image);
            articleAbstract = (TextView)itemView.findViewById(R.id.article_info_cardview);
            ago = (TextView)itemView.findViewById(R.id.ago);
            doc = new Doc();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.onItemClick(itemView, getLayoutPosition());
                }
            });

        }

    }

    public void setData(ArrayList<Doc> data) {
        this.data = data;
    }

    @Override
    public NewsRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recyclerview_layout, parent, false);
        NewsRecyclerViewHolder vh = new NewsRecyclerViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(NewsRecyclerViewHolder holder, int position) {
        long timeStamp = System.currentTimeMillis();
        holder.headline.setText(data.get(position).getHeadline().getMain());
        holder.articleAbstract.setText(data.get(position).getLead_paragraph());
        String agoText = NewsRecyclerAdapter.getBiggestUnitTimeElapsed(data.get(position).getPub_date(), timeStamp);
        if(agoText.isEmpty()) {
            holder.ago.setText("published today");
        } else {
            holder.ago.setText("published " + NewsRecyclerAdapter.getBiggestUnitTimeElapsed(data.get(position).getPub_date(), timeStamp) + " ago");
        }

        String imageURI = null;
        Multimedia[] multiMedia = data.get(position).getMultimedia();
        if(multiMedia != null && multiMedia.length > 0) {
             imageURI = multiMedia[0].getUrl();
        }
            if (imageURI == null) {
                imageURI = "R.drawable.nyt_icon";
            }

            Picasso.with(context)
                    .load("http://nytimes.com/" + imageURI)
                    .placeholder(R.drawable.nyt_icon)
                    .resize(100, 100)
                    .centerCrop()
                    .into(holder.imageIcon);
    }

    @Override
    public int getItemCount() {
        return data.size();

    }
}
