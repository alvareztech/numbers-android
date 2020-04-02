package tech.alvarez.numbers.networking

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tech.alvarez.numbers.util.Constants

object RetrofitService {

    private val client = OkHttpClient.Builder().build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.YOUTUBE_DATA_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun <T> createService(service: Class<T>): T {
        return retrofit.create(service)
    }
}