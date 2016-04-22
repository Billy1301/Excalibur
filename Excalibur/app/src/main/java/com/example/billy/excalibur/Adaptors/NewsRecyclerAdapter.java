package com.example.billy.excalibur.Adaptors;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.billy.excalibur.NyTimesAPIService.NewsWireObjects;
import com.example.billy.excalibur.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Mikhail on 4/17/16.
 */
public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.NewsRecyclerViewHolder> {

    ArrayList<NewsWireObjects> data;
    private String TAG = "RecyclerViewAdaptor";
    Context context;
    private static OnItemClickListener listener;

    public NewsRecyclerAdapter(ArrayList<NewsWireObjects> data) {
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
        NewsWireObjects newsWireObjects;

        public NewsRecyclerViewHolder(final View itemView) {
            super(itemView);

            headline = (TextView) itemView.findViewById(R.id.headline);
            imageIcon = (ImageView)itemView.findViewById(R.id.cardView_image);
            articleAbstract = (TextView)itemView.findViewById(R.id.article_info_cardview);
            newsWireObjects = new NewsWireObjects();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
            public void onClick(View v) {
                    if (listener != null)
                    listener.onItemClick(itemView, getLayoutPosition());
                }
            });

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

        return vh;
    }


    @Override
    public void onBindViewHolder(NewsRecyclerViewHolder holder, int position) {
        //TODO: Set our textView to our data - News object
        holder.headline.setText(data.get(position).getTitle());
        holder.articleAbstract.setText(data.get(position).getAbstractResult());


        String imageURI = data.get(position).getThumbnail_standard();
        if(imageURI.isEmpty()){
            imageURI = "R.drawable.nyt_icon";
        }

        Picasso.with(context)
                .load(imageURI)
                .placeholder(R.drawable.nyt_icon)
                .resize(100, 100)
                .centerCrop()
                .into(holder.imageIcon);
        holder.articleAbstract.setText(data.get(position).getAbstractResult());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static String getBiggestUnitTimeElapsed(long timeStamp, long nowStamp){
        String time = "";
        long different = nowStamp - timeStamp;
        String[] unit = {"year", "month", "day", "hour", "minute", "second"};
        int numberOfUnits = unit.length;
        long[] multiple = {31536000000l, 2592000000l, 86400000l, 3600000l, 60000l, 1000l}; //units in millis
        long[] coef = {0,0,0,0,0,0};
//        String[] coefUnit = new String[6];
        for(int i = 0; i < numberOfUnits; i++){
            coef[i] = different / multiple[i];
            different = different % multiple[i];
        }

        for(int i = 0; i < numberOfUnits; i++){
            if(coef[i] > 0) {
                time = coef[i] + " " + unit[i];
                if(coef[i] > 1){
                    time = time + "s";
                    break;
                }
            }
        }
        return time;
    }



}

