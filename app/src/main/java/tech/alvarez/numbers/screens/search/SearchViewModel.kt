package tech.alvarez.numbers.screens.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tech.alvarez.numbers.ChannelsRepository
import tech.alvarez.numbers.model.Item
import tech.alvarez.numbers.util.Constants

class SearchViewModel : ViewModel() {

    val channels: MutableLiveData<List<Item>> by lazy {
        MutableLiveData<List<Item>>()
    }

    init {
        val repository = ChannelsRepository()
        viewModelScope.launch {
            val search = repository.search()
            Log.i(Constants.TAG, "**>> $search")
            channels.value = search.items
        }
    }
}
