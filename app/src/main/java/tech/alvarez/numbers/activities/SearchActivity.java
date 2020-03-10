package tech.alvarez.numbers.activities;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import tech.alvarez.numbers.R;
import tech.alvarez.numbers.adapters.OnItemClickListener;
import tech.alvarez.numbers.adapters.SearchChannelsAdapter;
import tech.alvarez.numbers.db.entity.ChannelEntity;
import tech.alvarez.numbers.model.youtube.search.ItemSearchResponse;
import tech.alvarez.numbers.model.youtube.search.SearchResponse;
import tech.alvarez.numbers.utils.Constants;
import tech.alvarez.numbers.utils.Messages;
import tech.alvarez.numbers.viewmodel.SearchViewModel;

public class SearchActivity extends AppCompatActivity implements OnItemClickListener {

    private SearchViewModel mViewModel;

    private RecyclerView resultsRecyclerView;
    private SearchChannelsAdapter channelsAdapter;
    private CoordinatorLayout coordinatorLayout;

    private EditText queryEditText;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mViewModel = new ViewModelProvider(this).get(SearchViewModel.class);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

    public void search(final String query) {
        Log.d(Constants.TAG, "search: " + query);

        progressBar.setVisibility(View.VISIBLE);

        mViewModel.getSearchObservable(query).subscribe(new Consumer<SearchResponse>() {
            @Override
            public void accept(SearchResponse searchResponse) throws Exception {
                Log.d(Constants.TAG, "  onResponse: " + searchResponse.toString());
                progressBar.setVisibility(View.GONE);
                setData(searchResponse.getItems());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                progressBar.setVisibility(View.GONE);
                Log.e(Constants.TAG, " onFailure: " + throwable.toString());
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

        int count = mViewModel.countChannels();

        if (count < Constants.LIMIT_YOUTUBE_CHANNELS) {
            ChannelEntity channel = new ChannelEntity();
            channel.setId(itemSearchResponse.getSnippet().getChannelId());
            channel.setName(itemSearchResponse.getSnippet().getTitle());
            channel.setDescription(itemSearchResponse.getSnippet().getDescription());
            channel.setProfileUrl(itemSearchResponse.getSnippet().getThumbnails().getDefaultThumbnail().getUrl());

            mViewModel.insert(channel);
            channelsAdapter.notifyDataSetChanged();
        } else {
            Snackbar.make(resultsRecyclerView, R.string.no_add_more_channels, Snackbar.LENGTH_SHORT).show();
        }
    }
}
