package tech.alvarez.numbers.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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

import io.realm.Realm;
import io.realm.RealmChangeListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tech.alvarez.numbers.BuildConfig;
import tech.alvarez.numbers.R;
import tech.alvarez.numbers.models.db.ChannelRealm;
import tech.alvarez.numbers.models.youtube.ChannelsResponse;
import tech.alvarez.numbers.models.youtube.ItemResponse;
import tech.alvarez.numbers.utils.Constants;
import tech.alvarez.numbers.utils.Messages;
import tech.alvarez.numbers.utils.Util;
import tech.alvarez.numbers.youtube.RetrofitClient;
import tech.alvarez.numbers.youtube.YouTubeDataApi;

public class ChannelActivity extends AppCompatActivity {

    private Realm realm;

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
    private Call<ChannelsResponse> call;

    private ChannelRealm channelRealm;
    private RealmChangeListener realmListener = new RealmChangeListener() {
        @Override
        public void onChange(Object element) {
            Log.d(Constants.TAG, " onChange");

            setDataFromRealm();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);

        realm = Realm.getDefaultInstance();
        realm.addChangeListener(realmListener);

        Intent intent = getIntent();
        channelId = intent.getStringExtra("channel_id");

        timer = new Timer();


        channelRealm = realm.where(ChannelRealm.class).equalTo("id", channelId).findFirst();

        initViews();
        setDataFromRealm();
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
        timer.schedule(timerTask, 0, 3000);
    }

    public void getChannelFromAPI() {
        Log.i(Constants.TAG, " getChannelFromAPI");

        YouTubeDataApi service = RetrofitClient.getClient().create(YouTubeDataApi.class);
        call = service.getChannelsWithDetails(BuildConfig.YOUTUBE_DATA_API_KEY, channelId);

        Log.d(Constants.TAG, "  URL: " + call.request().url());

        call.enqueue(new Callback<ChannelsResponse>() {
            @Override
            public void onResponse(Call<ChannelsResponse> call, Response<ChannelsResponse> response) {
                Log.d(Constants.TAG, "  onResponse");

                if (response.isSuccessful()) {
                    ArrayList<ItemResponse> itemResponses = response.body().getItems();
                    if (itemResponses != null && itemResponses.size() > 0) {
                        saveDataToRealm(itemResponses.get(0));
                    }
                } else {
                    Log.e(Constants.TAG, " Error: " + response.errorBody());

                    Messages.showNetwotkError(fab, ChannelActivity.this);
                }
            }

            @Override
            public void onFailure(Call<ChannelsResponse> call, Throwable t) {
                Log.e(Constants.TAG, "  onFailure: " + t.getMessage());

                Messages.showNetwotkError(fab, ChannelActivity.this);
            }
        });

    }

    private void saveDataToRealm(ItemResponse itemResponse) {
        Log.d(Constants.TAG, " saveDataToRealm");

        if (channelRealm != null) {
            realm.beginTransaction();
            channelRealm.setProfileUrl(itemResponse.getSnippet().getThumbnails().getDefaultThumbnail().getUrl());
            channelRealm.setSubscribers(Integer.parseInt(itemResponse.getStatistics().getSubscriberCount()));
            channelRealm.setDescription(itemResponse.getSnippet().getDescription());
            channelRealm.setVideos(Long.parseLong(itemResponse.getStatistics().getVideoCount()));
            channelRealm.setViews(Long.parseLong(itemResponse.getStatistics().getViewCount()));
            realm.commitTransaction();
        }
    }


    private void setDataFromRealm() {
        Log.d(Constants.TAG, " setDataFromRealm");

        nameTextView.setText(channelRealm.getName());
        viewsTextView.setText(NumberFormat.getInstance().format(channelRealm.getViews()));
        subsTextView.setText(NumberFormat.getInstance().format(channelRealm.getSubscribers()));
        videosTextView.setText(NumberFormat.getInstance().format(channelRealm.getVideos()));
        descTextView.setText(channelRealm.getDescription());
        fab.setImageResource(channelRealm.isFavorite() ? R.drawable.ic_star_white_24dp : R.drawable.ic_star_border_white_24dp);

//        Log.d(Constants.TAG, channelRealm.getBannerUrl() == null ? "null" : channelRealm.getBannerUrl());

        Glide.with(getApplicationContext())
                .load(channelRealm.getBannerUrl())
                .centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(bannerImageView);

        Glide.with(getApplicationContext())
                .load(channelRealm.getProfileUrl())
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
        ChannelRealm channelRealm = realm.where(ChannelRealm.class).equalTo("id", channelId).findFirst();

        Intent share = new Intent(Intent.ACTION_SEND);
        share.putExtra(Intent.EXTRA_SUBJECT, channelRealm.getName());
        share.putExtra(Intent.EXTRA_TEXT, Util.getURLChannel(channelId));
        share.setType("text/plain");
        startActivity(Intent.createChooser(share, getString(R.string.shareChannel)));
    }

    private void removeChannel() {
        Log.d(Constants.TAG, "removeChannel");
        realm.removeAllChangeListeners();
        call.cancel();
        timer.cancel();
        ChannelRealm channelRealm = realm.where(ChannelRealm.class).equalTo("id", channelId).findFirst();
        realm.beginTransaction();
        channelRealm.deleteFromRealm();
        realm.commitTransaction();
        finish();
    }

    @Override
    protected void onStop() {
        Log.d(Constants.TAG, "onStop");
        super.onStop();
        timer.cancel();
    }

    @Override
    protected void onDestroy() {
        Log.d(Constants.TAG, "onDestroy");
        super.onDestroy();
        call.cancel();
        realm.close();
    }


    public void setFavorite(View view) {
        Log.d(Constants.TAG, " setFavorite");
        boolean isFavorite = channelRealm.isFavorite();
        realm.beginTransaction();
        channelRealm.setFavorite(!isFavorite);
        realm.commitTransaction();

        if (!isFavorite) {
            fab.setImageResource(R.drawable.ic_star_white_24dp);
        } else {
            fab.setImageResource(R.drawable.ic_star_border_white_24dp);
        }
    }
}
