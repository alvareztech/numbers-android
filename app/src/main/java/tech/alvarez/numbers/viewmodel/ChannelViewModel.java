package tech.alvarez.numbers.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import tech.alvarez.numbers.BuildConfig;
import tech.alvarez.numbers.db.AppDatabase;
import tech.alvarez.numbers.db.entity.ChannelEntity;
import tech.alvarez.numbers.model.youtube.ChannelsResponse;
import tech.alvarez.numbers.youtube.RetrofitHelper;

public class ChannelViewModel extends AndroidViewModel {

    private AppDatabase mDb;
    public LiveData<ChannelEntity> channel;

    public ChannelViewModel(Application application) {
        super(application);

        mDb = AppDatabase.getDatabase(this.getApplication());
    }

    public LiveData<ChannelEntity> getChannelLiveData(String channelId) {
        return mDb.channelModel().findChannel(channelId);
    }

    public void delete(ChannelEntity channel) {
        mDb.channelModel().deleteChannel(channel);
    }

    public void update(ChannelEntity channel) {
        mDb.channelModel().updateChannel(channel);
    }

    public Observable<ChannelsResponse> getChannelObservable(String channelId) {
        return RetrofitHelper.getYouTubeService().getChannelsWithDetails2(BuildConfig.YOUTUBE_DATA_API_KEY, channelId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
