package tech.alvarez.numbers.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tech.alvarez.numbers.BuildConfig;
import tech.alvarez.numbers.R;
import tech.alvarez.numbers.adapters.ChannelSubsAdapter;
import tech.alvarez.numbers.adapters.ChannelSubsItemClickListener;
import tech.alvarez.numbers.adapters.FavoritesAdapter;
import tech.alvarez.numbers.models.db.ChannelRealm;
import tech.alvarez.numbers.models.youtube.ChannelsResponse;
import tech.alvarez.numbers.models.youtube.ItemResponse;
import tech.alvarez.numbers.utils.Constants;
import tech.alvarez.numbers.utils.Database;
import tech.alvarez.numbers.utils.Messages;
import tech.alvarez.numbers.utils.Util;
import tech.alvarez.numbers.youtube.RetrofitClient;
import tech.alvarez.numbers.youtube.YouTubeDataApi;

public class MainActivity extends AppCompatActivity implements ChannelSubsItemClickListener {

    private Realm realm;
    private Call<ChannelsResponse> call;

    private CoordinatorLayout coordinatorLayout;
    private FloatingActionButton fab;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView rankingRecyclerView;
    private FavoritesAdapter favoritesAdapter;
    private RecyclerView favoriteRecyclerView;
    private ChannelSubsAdapter channelSubsAdapter;
    private RelativeLayout initLayout;

    private RealmResults<ChannelRealm> results;
    private RealmResults<ChannelRealm> favorites;

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
        setContentView(R.layout.activity_main);

        realm = Realm.getDefaultInstance();
        realm.addChangeListener(realmListener);

        results = realm.where(ChannelRealm.class).findAll();
        favorites = realm.where(ChannelRealm.class).equalTo("favorite", true).findAll();

        initViews();
    }

    private void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        initLayout = (RelativeLayout) findViewById(R.id.initLayout);

        favoriteRecyclerView = (RecyclerView) findViewById(R.id.favoriteRecyclerView);
        favoriteRecyclerView.setHasFixedSize(true);
        favoriteRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        favoritesAdapter = new FavoritesAdapter(this);
        favoriteRecyclerView.setAdapter(favoritesAdapter);


        rankingRecyclerView = (RecyclerView) findViewById(R.id.rankingRecyclerView);
        rankingRecyclerView.setHasFixedSize(true);
        rankingRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        rankingRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    // Scroll Down
                    if (fab.isShown()) {
                        fab.hide();
                    }
                } else if (dy < 0) {
                    // Scroll Up
                    if (!fab.isShown()) {
                        fab.show();
                    }
                }
            }
        });
//        rankingRecyclerView.setItemAnimator(new DefaultItemAnimator());

//        Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.divider_sample);
//        RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(dividerDrawable);
//        rankingRecyclerView.addItemDecoration(dividerItemDecoration);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i(Constants.TAG, " onRefresh");
                getChannelsFromAPI();
            }
        });

        channelSubsAdapter = new ChannelSubsAdapter(this, this);
        rankingRecyclerView.setAdapter(channelSubsAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.i(Constants.TAG, "onStart");

        setDataFromRealm();
        getChannelsFromAPI();
    }

    public void getChannelsFromAPI() {
        Log.i(Constants.TAG, " getChannelsFromAPI");

        String ids = Database.getIdChannelsInRealm(realm);

        YouTubeDataApi service = RetrofitClient.getClient().create(YouTubeDataApi.class);
        call = service.getChannels(BuildConfig.YOUTUBE_DATA_API_KEY, ids);

        Log.i(Constants.TAG, "  URL: " + call.request().url());

        call.enqueue(new Callback<ChannelsResponse>() {
            @Override
            public void onResponse(Call<ChannelsResponse> call, Response<ChannelsResponse> response) {
                Log.i(Constants.TAG, "   onResponse");
                swipeRefreshLayout.setRefreshing(false);

                if (response.isSuccessful()) {
                    saveDataToRealm(response.body().getItems());
                } else {
                    Log.e(Constants.TAG, " Error: " + response.errorBody());

                    Messages.showNetwotkError(coordinatorLayout, MainActivity.this);
                }
            }

            @Override
            public void onFailure(Call<ChannelsResponse> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);

                Messages.showNetwotkError(coordinatorLayout, MainActivity.this);
            }
        });
    }

    private void saveDataToRealm(ArrayList<ItemResponse> items) {
        Log.d(Constants.TAG, "saveDataToRealm");
        if (items != null && items.size() > 0) {
            realm.beginTransaction();
            for (ItemResponse itemResponse : items) {
                ChannelRealm channelRealm = realm.where(ChannelRealm.class).equalTo("id", itemResponse.getId()).findFirst();
                channelRealm.setName(itemResponse.getSnippet().getTitle());
                channelRealm.setDescription(itemResponse.getSnippet().getDescription());
                channelRealm.setProfileUrl(itemResponse.getSnippet().getThumbnails().getDefaultThumbnail().getUrl());
                channelRealm.setSubscribers(Integer.parseInt(itemResponse.getStatistics().getSubscriberCount()));
                channelRealm.setViews(Long.parseLong(itemResponse.getStatistics().getViewCount()));
                channelRealm.setVideos(Long.parseLong(itemResponse.getStatistics().getVideoCount()));
                channelRealm.setBannerUrl(itemResponse.getBrandingSettings().getImage().getBannerMobileImageUrl());
                channelRealm.setHiddenSubscribers(itemResponse.getStatistics().isHiddenSubscriberCount());
            }
            realm.commitTransaction();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i(Constants.TAG, " onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem ascendingItemMenu = menu.findItem(R.id.ascendingItemMenu);
        ascendingItemMenu.setChecked(Util.getOrderPreference(this) == Constants.ASCENDING_ORDER);

        MenuItem differenceItemMenu = menu.findItem(R.id.differenceItemMenu);
        SwitchCompat aSwitch = (SwitchCompat) differenceItemMenu.getActionView();
        aSwitch.setChecked(Util.isEnableDifference(this));
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                showOrHideDifference(b);
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void showOrHideDifference(boolean checked) {
        Log.i(Constants.TAG, " showOrHideDifference: " + checked);
        if (checked) {
            Util.setEnableDifference(this);
        } else {
            Util.setDisableDifference(this);
        }
        setDataFromRealm();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(Constants.TAG, " onOptionsItemSelected");
        if (item.getItemId() == R.id.ascendingItemMenu) {
            Util.setOrderPreference(item.isChecked() ? Constants.DESCENDING_ORDER : Constants.ASCENDING_ORDER, this);
            boolean isAS = Util.getOrderPreference(this) == Constants.ASCENDING_ORDER;
            Log.i(Constants.TAG, " onOptionsItemSelected " + isAS);

            item.setChecked(isAS);
            setDataFromRealm();
        } else if (item.getItemId() == R.id.updateItemMenu) {
            getChannelsFromAPI();


//        } else if (item.getItemId() == R.id.subsItemMenu) {
//            Util.setTypeSortPreference(Constants.TYPE_SORT_SUBS, this);
//            setDataFromRealm();
//        } else if (item.getItemId() == R.id.viewsItemMenu) {
//            Util.setTypeSortPreference(Constants.TYPE_SORT_VIEWS, this);
//            setDataFromRealm();
        } else if (item.getItemId() == R.id.creditsItemMenu) {
            openCreditsScreen();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(Constants.TAG, "onDestroy");
        call.cancel();
        realm.close();
    }

    @Override
    public void onChannelItemClick(ChannelRealm channelRealm) {
        try {
            Intent intent = new Intent(this, ChannelActivity.class);
            intent.putExtra("channel_id", channelRealm.getId());
            startActivity(intent);
        } catch (Exception ex) {
            Log.e(Constants.TAG, " onChannelItemClick: " + ex.getMessage());
        }
    }

    public void setDataFromRealm() {
        Log.d(Constants.TAG, " setDataFromRealm");

        if (results.size() > 0) {
            swipeRefreshLayout.setVisibility(View.VISIBLE);
            initLayout.setVisibility(View.GONE);
            if (Util.getOrderPreference(this) == Constants.ASCENDING_ORDER) {
                results = results.sort("subscribers", Sort.ASCENDING);
            } else {
                results = results.sort("subscribers", Sort.DESCENDING);
            }
            channelSubsAdapter.setDataset(results.subList(0, results.size()));
        } else {
            swipeRefreshLayout.setVisibility(View.GONE);
            initLayout.setVisibility(View.VISIBLE);
        }

        if (favorites.size() > 0) {
            favoriteRecyclerView.setVisibility(View.VISIBLE);
            if (Util.getOrderPreference(this) == Constants.ASCENDING_ORDER) {
                favorites = favorites.sort("subscribers", Sort.ASCENDING);
            } else {
                favorites = favorites.sort("subscribers", Sort.DESCENDING);
            }
            favoritesAdapter.setDataset(favorites.subList(0, favorites.size()));
        } else {
            favoriteRecyclerView.setVisibility(View.GONE);
        }
    }

    private void openCreditsScreen() {
        Intent intent = new Intent(this, CreditsActivity.class);
        startActivity(intent);
    }

    public void openSearchScreen(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    public void addFirstChannel(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }
}

