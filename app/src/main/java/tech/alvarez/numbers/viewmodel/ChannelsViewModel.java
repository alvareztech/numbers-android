package tech.alvarez.numbers.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import tech.alvarez.numbers.BuildConfig;
import tech.alvarez.numbers.db.AppDatabase;
import tech.alvarez.numbers.db.entity.ChannelEntity;
import tech.alvarez.numbers.model.youtube.ChannelsResponse;
import tech.alvarez.numbers.youtube.RetrofitHelper;

public class ChannelsViewModel extends AndroidViewModel {

    public final LiveData<List<ChannelEntity>> channels;
    public final LiveData<List<ChannelEntity>> favoriteChannels;

    private AppDatabase mDb;

    public ChannelsViewModel(Application application) {
        super(application);

        mDb = AppDatabase.getDatabase(this.getApplication());
        channels = mDb.channelModel().findAllChannels();
        favoriteChannels = mDb.channelModel().findFavoriteChannels();
    }

    public void update(List<ChannelEntity> channels) {
        mDb.channelModel().updateChannel(channels);
    }

    public Observable<ChannelsResponse> getChannelsObservable(String channelId) {
        return RetrofitHelper.getYouTubeService().getChannels2(BuildConfig.YOUTUBE_DATA_API_KEY, channelId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
