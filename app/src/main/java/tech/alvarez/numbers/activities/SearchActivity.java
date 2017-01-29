package tech.alvarez.numbers.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tech.alvarez.numbers.BuildConfig;
import tech.alvarez.numbers.R;
import tech.alvarez.numbers.adapters.OnItemClickListener;
import tech.alvarez.numbers.adapters.SearchChannelsAdapter;
import tech.alvarez.numbers.models.db.ChannelRealm;
import tech.alvarez.numbers.models.youtube.search.ItemSearchResponse;
import tech.alvarez.numbers.models.youtube.search.SearchResponse;
import tech.alvarez.numbers.utils.Constants;
import tech.alvarez.numbers.utils.Database;
import tech.alvarez.numbers.utils.Messages;
import tech.alvarez.numbers.youtube.RetrofitClient;
import tech.alvarez.numbers.youtube.YouTubeDataApi;

public class SearchActivity extends AppCompatActivity implements OnItemClickListener {

    private Realm realm;

    private RecyclerView resultsRecyclerView;
    private SearchChannelsAdapter channelsAdapter;
    private CoordinatorLayout coordinatorLayout;

    private EditText queryEditText;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        realm = Realm.getDefaultInstance();

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        resultsRecyclerView = (RecyclerView) findViewById(R.id.resultsRecyclerView);
        resultsRecyclerView.setHasFixedSize(true);
        resultsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        channelsAdapter = new SearchChannelsAdapter(this, this);
        resultsRecyclerView.setAdapter(channelsAdapter);

        queryEditText = (EditText) findViewById(R.id.queryEditText);
        queryEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                hideKeyboard();
                search(textView.getText().toString());
                return false;
            }
        });
        queryEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.e(Constants.TAG, charSequence + "");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_search, menu);
//        return true;
//    }

    public void search(final String query) {
        YouTubeDataApi service = RetrofitClient.getClient().create(YouTubeDataApi.class);
        Call<SearchResponse> call = service.search(query, BuildConfig.YOUTUBE_DATA_API_KEY, "snippet", "channel");
        Log.d(Constants.TAG, call.request().url().toString());

        progressBar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                Log.d(Constants.TAG, " onResponse");
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {
                    Log.d(Constants.TAG, "  onResponse.isSuccessful " + response.raw());
                    setData(response.body().getItems());
                } else {
                    Log.e(Constants.TAG, " " + response.errorBody());
                    Messages.showNetwotkError(coordinatorLayout, SearchActivity.this, query);
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                t.printStackTrace();
                progressBar.setVisibility(View.GONE);
                Log.e(Constants.TAG, " onFailure: " + t.toString());
                Messages.showNetwotkError(coordinatorLayout, SearchActivity.this, query);
            }
        });

    }

    private void setData(ArrayList<ItemSearchResponse> items) {
        if (items != null && items.size() > 0) {
            channelsAdapter.setDataset(items);
        }
    }


    @Override
    public void onItemClick(final ItemSearchResponse itemSearchResponse) {

        if (Database.countChannels(realm) < Constants.LIMIT_YOUTUBE_CHANNELS) {

            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    ChannelRealm channel = realm.createObject(ChannelRealm.class);
                    channel.setId(itemSearchResponse.getSnippet().getChannelId());
                    channel.setName(itemSearchResponse.getSnippet().getTitle());
                    channel.setDescription(itemSearchResponse.getSnippet().getDescription());
                    channel.setProfileUrl(itemSearchResponse.getSnippet().getThumbnails().getDefaultThumbnail().getUrl());
                }
            }, new Realm.Transaction.OnSuccess() {
                @Override
                public void onSuccess() {
                    Snackbar.make(resultsRecyclerView, R.string.added_channel, Snackbar.LENGTH_SHORT).show();
                    channelsAdapter.notifyDataSetChanged();
                }
            }, new Realm.Transaction.OnError() {
                @Override
                public void onError(Throwable error) {
                    Snackbar.make(resultsRecyclerView, error.getLocalizedMessage(), Snackbar.LENGTH_SHORT).show();
                }
            });
        } else {
            Snackbar.make(resultsRecyclerView, R.string.no_add_more_channels, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}