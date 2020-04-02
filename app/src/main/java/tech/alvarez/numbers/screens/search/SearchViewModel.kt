package tech.alvarez.numbers.screens.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tech.alvarez.numbers.BuildConfig
import tech.alvarez.numbers.model.ItemSearchResponse
import tech.alvarez.numbers.model.Search
import tech.alvarez.numbers.networking.RetrofitService
import tech.alvarez.numbers.networking.YouTubeData
import tech.alvarez.numbers.util.Constants

class SearchViewModel : ViewModel() {

    val channels: MutableLiveData<List<ItemSearchResponse>> by lazy {
        MutableLiveData<List<ItemSearchResponse>>()
    }

    init {
        val apiService = RetrofitService.createService(YouTubeData::class.java)
        val call = apiService.search(
            "alvarez tech",
            BuildConfig.YOUTUBE_DATA_API_KEY,
            "snippet",
            "channel"
        )
        call.enqueue(object : Callback<Search> {
            override fun onResponse(call: Call<Search>, response: Response<Search>) {
                if (response.isSuccessful) {
                    channels.value = response.body()?.items
                }
            }

            override fun onFailure(call: Call<Search>, t: Throwable) {
                Log.i(Constants.TAG, t.message)
            }
        })
    }
}
