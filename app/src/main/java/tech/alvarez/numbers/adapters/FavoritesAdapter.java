package tech.alvarez.numbers.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import tech.alvarez.numbers.R;
import tech.alvarez.numbers.db.entity.ChannelEntity;

/**
 * Created by Daniel Alvarez on 8/15/16.
 * Copyright © 2016 Alvarez.tech. All rights reserved.
 */
public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {

    private List<ChannelEntity> dataset;
    private Context context;

    public FavoritesAdapter(Context context) {
        this.context = context;
        this.dataset = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ChannelEntity channelRealm = dataset.get(position);
        holder.subsTextView.setText(NumberFormat.getInstance().format(channelRealm.getSubscribers()));

        Glide.with(context).load(channelRealm.getProfileUrl())
                .centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.profileImageView);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView subsTextView;
        private ImageView profileImageView;

        public ViewHolder(View itemView) {
            super(itemView);

            subsTextView = (TextView) itemView.findViewById(R.id.subsTextView);
            profileImageView = (ImageView) itemView.findViewById(R.id.profileImageView);
        }
    }

    public void setDataset(List<ChannelEntity> dataset) {
        this.dataset = dataset;
        notifyDataSetChanged();
    }
}
