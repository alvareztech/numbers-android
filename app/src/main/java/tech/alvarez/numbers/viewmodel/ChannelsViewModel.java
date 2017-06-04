package tech.alvarez.numbers.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import tech.alvarez.numbers.db.AppDatabase;
import tech.alvarez.numbers.db.entity.ChannelEntity;

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
}
