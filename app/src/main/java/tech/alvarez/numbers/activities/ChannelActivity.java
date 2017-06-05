package tech.alvarez.numbers.activities;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import tech.alvarez.numbers.R;
import tech.alvarez.numbers.db.entity.ChannelEntity;
import tech.alvarez.numbers.model.youtube.ChannelsResponse;
import tech.alvarez.numbers.model.youtube.ItemResponse;
import tech.alvarez.numbers.utils.Constants;
import tech.alvarez.numbers.utils.Messages;
import tech.alvarez.numbers.utils.Util;
import tech.alvarez.numbers.viewmodel.ChannelViewModel;

public class ChannelActivity extends AppCompatActivity implements LifecycleRegistryOwner {

    private ChannelViewModel mViewModel;
    private final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

    private TextView nameTextView;
    private TextView subsTextView;
    private TextView viewsTextView;
    private TextView videosTextView;
    private TextView descTextView;
    private ImageView profileImageView;
    private ImageView bannerImageView;
    private FloatingActionButton fab;

    private String channelId;

    private Timer timer;

    private ChannelEntity channelEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);

        mViewModel = ViewModelProviders.of(this).get(ChannelViewModel.class);

        Intent intent = getIntent();
        channelId = intent.getStringExtra("channel_id");

        timer = new Timer();

        initViews();
        subscribeUiChannel();
    }

    private void subscribeUiChannel() {
        mViewModel.getChannelLiveData(channelId).observe(this, new Observer<ChannelEntity>() {
            @Override
            public void onChanged(@Nullable ChannelEntity channel) {
                Log.e(Constants.TAG, "onChanged");
                channelEntity = channel;
                setChannelUi();
            }
        });
    }

    private void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        nameTextView = (TextView) findViewById(R.id.nameTextView);
        subsTextView = (TextView) findViewById(R.id.subsTextView);
        viewsTextView = (TextView) findViewById(R.id.viewsTextView);
        videosTextView = (TextView) findViewById(R.id.videosTextView);
        descTextView = (TextView) findViewById(R.id.descTextView);
        profileImageView = (ImageView) findViewById(R.id.profileImageView);
        bannerImageView = (ImageView) findViewById(R.id.bannerImageView);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        getSupportActionBar().setTitle("");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.i(Constants.TAG, "onStart");

        initTimer();
    }

    private void initTimer() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Log.d(Constants.TAG, "  Task");
                getChannelFromAPI();
            }
        };
        timer = new Timer();
        timer.schedule(timerTask, 0, Constants.FREQUENCY_UPDATE);
    }

    public void getChannelFromAPI() {
        Log.i(Constants.TAG, " getChannelFromAPI");

        mViewModel.getChannelObservable(channelId).subscribe(new Consumer<ChannelsResponse>() {
            @Override
            public void accept(@NonNull ChannelsResponse channelsResponse) throws Exception {
                ArrayList<ItemResponse> itemResponses = channelsResponse.getItems();
                if (itemResponses != null && itemResponses.size() > 0) {
                    saveDatabase(itemResponses.get(0));
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                Messages.showNetwotkError(fab, ChannelActivity.this);
            }
        });
    }

    private void saveDatabase(ItemResponse itemResponse) {
        Log.d(Constants.TAG, " saveDatabase");

        if (channelEntity != null) {
            channelEntity.setProfileUrl(itemResponse.getSnippet().getThumbnails().getDefaultThumbnail().getUrl());
            channelEntity.setSubscribers(Integer.parseInt(itemResponse.getStatistics().getSubscriberCount()));
            channelEntity.setDescription(itemResponse.getSnippet().getDescription());
            channelEntity.setVideos(Long.parseLong(itemResponse.getStatistics().getVideoCount()));
            channelEntity.setViews(Long.parseLong(itemResponse.getStatistics().getViewCount()));
            mViewModel.update(channelEntity);
        }
    }


    private void setChannelUi() {
        Log.d(Constants.TAG, " setChannelUi");

        nameTextView.setText(channelEntity.getName());
        viewsTextView.setText(NumberFormat.getInstance().format(channelEntity.getViews()));
        subsTextView.setText(NumberFormat.getInstance().format(channelEntity.getSubscribers()));
        videosTextView.setText(NumberFormat.getInstance().format(channelEntity.getVideos()));
        descTextView.setText(channelEntity.getDescription());
        fab.setImageResource(channelEntity.isFavorite() ? R.drawable.ic_star : R.drawable.ic_star_border);

//        Log.d(Constants.TAG, channelRealm.getBannerUrl() == null ? "null" : channelRealm.getBannerUrl());

        Glide.with(getApplicationContext())
                .load(channelEntity.getBannerUrl())
                .centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(bannerImageView);

        Glide.with(getApplicationContext())
                .load(channelEntity.getProfileUrl())
                .centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(profileImageView);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_channel, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.removeItemMenu) {
            removeChannel();
        } else if (item.getItemId() == R.id.openYouTubeItemMenu) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(Util.getURLChannel(channelId)));
            startActivity(intent);
        } else if (item.getItemId() == R.id.shareLink) {
            shareURL();
        }

        return super.onOptionsItemSelected(item);

    }

    private void shareURL() {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.putExtra(Intent.EXTRA_SUBJECT, channelEntity.getName());
        share.putExtra(Intent.EXTRA_TEXT, Util.getURLChannel(channelId));
        share.setType("text/plain");
        startActivity(Intent.createChooser(share, getString(R.string.shareChannel)));
    }

    private void removeChannel() {
        Log.d(Constants.TAG, "removeChannel");
        timer.cancel();

        mViewModel.delete(channelEntity);

        finish();
    }

    @Override
    protected void onStop() {
        Log.d(Constants.TAG, "onStop");
        super.onStop();
        timer.cancel();
    }

    public void setFavorite(View view) {
        Log.d(Constants.TAG, " setFavorite");
        boolean isFavorite = channelEntity.isFavorite();

        channelEntity.setFavorite(!isFavorite);
        mViewModel.update(channelEntity);

        if (!isFavorite) {
            fab.setImageResource(R.drawable.ic_star);
        } else {
            fab.setImageResource(R.drawable.ic_star_border);
        }
    }

    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }
}
