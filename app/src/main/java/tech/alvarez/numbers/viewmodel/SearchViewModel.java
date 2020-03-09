package tech.alvarez.numbers.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import tech.alvarez.numbers.BuildConfig;
import tech.alvarez.numbers.db.AppDatabase;
import tech.alvarez.numbers.db.entity.ChannelEntity;
import tech.alvarez.numbers.model.youtube.search.SearchResponse;
import tech.alvarez.numbers.youtube.RetrofitHelper;

public class SearchViewModel extends AndroidViewModel {

    private AppDatabase mDb;

    public SearchViewModel(Application application) {
        super(application);

        mDb = AppDatabase.getDatabase(this.getApplication());
    }

    public int countChannels() {
        return mDb.channelModel().getCountChannels();
    }

    public void insert(ChannelEntity channel) {
        mDb.channelModel().insertChannel(channel);
    }

    public Observable<SearchResponse> getSearchObservable(String query) {
        return RetrofitHelper.getYouTubeService().search2(query, BuildConfig.YOUTUBE_DATA_API_KEY, "snippet", "channel")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
