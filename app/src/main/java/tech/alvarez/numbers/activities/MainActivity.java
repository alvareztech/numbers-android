package tech.alvarez.numbers.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import tech.alvarez.numbers.R;
import tech.alvarez.numbers.adapters.ChannelSubsAdapter;
import tech.alvarez.numbers.adapters.ChannelSubsItemClickListener;
import tech.alvarez.numbers.adapters.FavoritesAdapter;
import tech.alvarez.numbers.db.entity.ChannelEntity;
import tech.alvarez.numbers.model.youtube.ChannelsResponse;
import tech.alvarez.numbers.model.youtube.ItemResponse;
import tech.alvarez.numbers.utils.Constants;
import tech.alvarez.numbers.utils.Messages;
import tech.alvarez.numbers.utils.Util;
import tech.alvarez.numbers.viewmodel.ChannelsViewModel;

public class MainActivity extends AppCompatActivity implements ChannelSubsItemClickListener {

    private ChannelsViewModel mViewModel;
    private final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

    private CoordinatorLayout coordinatorLayout;
    private FloatingActionButton fab;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView rankingRecyclerView;
    private FavoritesAdapter favoritesAdapter;
    private RecyclerView favoriteRecyclerView;
    private ChannelSubsAdapter channelSubsAdapter;
    private RelativeLayout initLayout;

    private List<ChannelEntity> results;
    private List<ChannelEntity> favorites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewModel = ViewModelProviders.of(this).get(ChannelsViewModel.class);

        initViews();
        subscribeUiChannels();
    }

    private void subscribeUiChannels() {
        Log.e(Constants.TAG, "subscribeUiChannels");
        mViewModel.channels.observe(this, new Observer<List<ChannelEntity>>() {
            @Override
            public void onChanged(@Nullable List<ChannelEntity> channelEntities) {
                Log.e(Constants.TAG, "> onChanged: " + channelEntities.size());
                results = channelEntities;
                setChannelsUi();
            }
        });
        mViewModel.favoriteChannels.observe(this, new Observer<List<ChannelEntity>>() {
            @Override
            public void onChanged(@Nullable List<ChannelEntity> channelEntities) {
                Log.e(Constants.TAG, "> onChanged favorites: " + channelEntities.size());
                favorites = channelEntities;
                setFavoriteChannelsUi();
            }
        });
    }

    private void setFavoriteChannelsUi() {
        if (favorites != null && favorites.size() > 0) {
            favoriteRecyclerView.setVisibility(View.VISIBLE);
//            if (Util.getOrderPreference(this) == Constants.ASCENDING_ORDER) {
//                favorites = mDb.channelModel().getFavoriteChannelsAsc();
//            } else {
//                favorites = mDb.channelModel().getFavoriteChannelsDesc();
//            }
            favoritesAdapter.setDataset(favorites);
        } else {
            favoriteRecyclerView.setVisibility(View.GONE);
        }
    }

    private void setChannelsUi() {
        if (results != null && results.size() > 0) {
            swipeRefreshLayout.setVisibility(View.VISIBLE);
            initLayout.setVisibility(View.GONE);
//            if (Util.getOrderPreference(this) == Constants.ASCENDING_ORDER) {
//                results = mDb.channelModel().getChannelsAsc();
//            } else {
//                results = mDb.channelModel().getChannelsDesc();
//            }
            channelSubsAdapter.setDataset(results);
        } else {
            swipeRefreshLayout.setVisibility(View.GONE);
            initLayout.setVisibility(View.VISIBLE);
        }
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
        getChannelsFromAPI();
    }

    public void getChannelsFromAPI() {
        Log.i(Constants.TAG, " getChannelsFromAPI");

        String ids = "";
        if (results == null) return;
        for (ChannelEntity channel : results) {
            ids += channel.getId() + ",";
        }

        Log.i(Constants.TAG, "  ids: " + ids);

        mViewModel.getChannelsObservable(ids).subscribe(new Consumer<ChannelsResponse>() {
            @Override
            public void accept(@NonNull ChannelsResponse channelsResponse) throws Exception {
                swipeRefreshLayout.setRefreshing(false);
                saveDatabase(channelsResponse.getItems());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                swipeRefreshLayout.setRefreshing(false);
                Messages.showNetwotkError(coordinatorLayout, MainActivity.this);
            }
        });
    }

    private void saveDatabase(ArrayList<ItemResponse> items) {
        Log.d(Constants.TAG, "saveDatabase");
        if (items != null && items.size() > 0) {
            List<ChannelEntity> entities = new ArrayList<>();
            for (ItemResponse itemResponse : items) {
                ChannelEntity channelEntity = new ChannelEntity();
                channelEntity.setId(itemResponse.getId());
                channelEntity.setName(itemResponse.getSnippet().getTitle());
                channelEntity.setDescription(itemResponse.getSnippet().getDescription());
                channelEntity.setProfileUrl(itemResponse.getSnippet().getThumbnails().getDefaultThumbnail().getUrl());
                channelEntity.setSubscribers(Integer.parseInt(itemResponse.getStatistics().getSubscriberCount()));
                channelEntity.setViews(Long.parseLong(itemResponse.getStatistics().getViewCount()));
                channelEntity.setVideos(Long.parseLong(itemResponse.getStatistics().getVideoCount()));
                channelEntity.setBannerUrl(itemResponse.getBrandingSettings().getImage().getBannerMobileImageUrl());
                channelEntity.setHiddenSubscribers(itemResponse.getStatistics().isHiddenSubscriberCount());
                entities.add(channelEntity);
            }
            mViewModel.update(entities);
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
        setChannelsUi();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(Constants.TAG, " onOptionsItemSelected");
        if (item.getItemId() == R.id.ascendingItemMenu) {
            Util.setOrderPreference(item.isChecked() ? Constants.DESCENDING_ORDER : Constants.ASCENDING_ORDER, this);
            boolean isAS = Util.getOrderPreference(this) == Constants.ASCENDING_ORDER;
            Log.i(Constants.TAG, " onOptionsItemSelected " + isAS);

            item.setChecked(isAS);
            setChannelsUi();
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
    public void onChannelItemClick(ChannelEntity channelEntity) {
        try {
            Intent intent = new Intent(this, ChannelActivity.class);
            intent.putExtra("channel_id", channelEntity.getId());
            startActivity(intent);
        } catch (Exception ex) {
            Log.e(Constants.TAG, " onChannelItemClick: " + ex.getMessage());
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

    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }
}

