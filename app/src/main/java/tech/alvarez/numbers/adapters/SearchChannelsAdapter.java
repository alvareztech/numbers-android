package tech.alvarez.numbers.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import tech.alvarez.numbers.R;
import tech.alvarez.numbers.models.youtube.search.ItemSearchResponse;
import tech.alvarez.numbers.utils.Database;

/**
 * Created by Daniel Alvarez on 8/7/16.
 * Copyright © 2016 Alvarez.tech. All rights reserved.
 */
public class SearchChannelsAdapter extends RecyclerView.Adapter<SearchChannelsAdapter.ViewHolder> {

    private Context context;
    private List<ItemSearchResponse> dataset;
    private OnItemClickListener onItemClickListener;

    public SearchChannelsAdapter(Context context, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.dataset = new ArrayList<>();
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_channel, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ItemSearchResponse item = dataset.get(position);
        holder.titleTextView.setText(item.getSnippet().getTitle());
        holder.descTextView.setText(item.getSnippet().getDescription());
        String url = "";

        if (item.getSnippet().getThumbnails().getDefaultThumbnail() != null) {
            url = item.getSnippet().getThumbnails().getDefaultThumbnail().getUrl();
        }
        if (Database.inTheDatabase(item.getSnippet().getChannelId())) {
            holder.addButton.setVisibility(View.GONE);
        } else {
            holder.addButton.setVisibility(View.VISIBLE);
        }

        Glide.with(context).load(url)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.photoImageView);

        holder.setOnItemClickListener(item, onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView photoImageView;
        private TextView titleTextView;
        private TextView descTextView;
        private Button addButton;

        public ViewHolder(View itemView) {
            super(itemView);
            photoImageView = (ImageView) itemView.findViewById(R.id.photoImageView);
            titleTextView = (TextView) itemView.findViewById(R.id.titleTextView);
            descTextView = (TextView) itemView.findViewById(R.id.descTextView);
            addButton = (Button) itemView.findViewById(R.id.addButton);
        }

        public void setOnItemClickListener(final ItemSearchResponse channel, final OnItemClickListener onItemClickListener) {
            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(channel);
                }
            });
        }
    }

    public List<ItemSearchResponse> getDataset() {
        return dataset;
    }

    public void setDataset(List<ItemSearchResponse> dataset) {
        this.dataset = dataset;
        notifyDataSetChanged();
    }
}
