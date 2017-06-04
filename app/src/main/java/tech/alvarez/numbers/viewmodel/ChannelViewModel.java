package tech.alvarez.numbers.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import tech.alvarez.numbers.db.AppDatabase;
import tech.alvarez.numbers.db.entity.ChannelEntity;

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
}
