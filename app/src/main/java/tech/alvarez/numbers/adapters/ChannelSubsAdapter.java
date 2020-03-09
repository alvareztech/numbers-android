package tech.alvarez.numbers.adapters;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import tech.alvarez.numbers.R;
import tech.alvarez.numbers.db.entity.ChannelEntity;
import tech.alvarez.numbers.utils.Util;

/**
 * Created by Daniel Alvarez on 24/7/16.
 * Copyright © 2016 Alvarez.tech. All rights reserved.
 */
public class ChannelSubsAdapter extends RecyclerView.Adapter<ChannelSubsAdapter.ViewHolder> {

    private Context context;
    private List<ChannelEntity> dataset;
    private ChannelSubsItemClickListener channelSubsItemClickListener;

    public ChannelSubsAdapter(Context context, ChannelSubsItemClickListener channelSubsItemClickListener) {
        this.channelSubsItemClickListener = channelSubsItemClickListener;
        this.context = context;
        this.dataset = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_channel_subs, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ChannelEntity channelRealm = dataset.get(position);
        holder.nameTextView.setText(channelRealm.getName());
        holder.subsTextView.setText(NumberFormat.getInstance().format(channelRealm.getSubscribers()));
        holder.subtitleTextView.setText(NumberFormat.getInstance().format(channelRealm.getVideos()) + " videos");

        if (position > 0) {
            ChannelEntity previousChannelRealm = dataset.get(position - 1);

            int difference = previousChannelRealm.getSubscribers() - channelRealm.getSubscribers();

            holder.differenceTextView.setText(NumberFormat.getInstance().format(difference < 0 ? difference * -1 : difference));
        } else {
            holder.differenceTextView.setText("");
        }

        if (Util.isEnableDifference(context)) {
            if (position == 0) {
                holder.differenceTextView.setVisibility(View.GONE);
            } else {
                holder.differenceTextView.setVisibility(View.VISIBLE);
            }
        } else {
            holder.differenceTextView.setVisibility(View.GONE);
        }

        holder.contentLayout.setBackgroundResource(channelRealm.isFavorite() ? R.drawable.item_background_favorite : R.drawable.item_background);

        Glide.with(context).load(channelRealm.getProfileUrl())
                .centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.profileImageView);

        holder.setOnItemClick(channelRealm, channelSubsItemClickListener);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTextView;
        private TextView subsTextView;
        private TextView subtitleTextView;
        private ImageView profileImageView;
        private TextView differenceTextView;
        private LinearLayout contentLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            contentLayout = (LinearLayout) itemView.findViewById(R.id.contentLayout);
            nameTextView = (TextView) itemView.findViewById(R.id.nameTextView);
            subsTextView = (TextView) itemView.findViewById(R.id.subsTextView);
            subtitleTextView = (TextView) itemView.findViewById(R.id.subtitleTextView);
            profileImageView = (ImageView) itemView.findViewById(R.id.profileImageView);
            differenceTextView = (TextView) itemView.findViewById(R.id.differenceTextView);
        }

        public void setOnItemClick(final ChannelEntity channelRealm, final ChannelSubsItemClickListener channelSubsItemClickListener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    channelSubsItemClickListener.onChannelItemClick(channelRealm);
                }
            });
        }
    }

    public List<ChannelEntity> getDataset() {
        return dataset;
    }

    public void setDataset(List<ChannelEntity> dataset) {
        this.dataset = dataset;
        notifyDataSetChanged();
    }
}
