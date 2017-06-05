package tech.alvarez.numbers.youtube;


import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import tech.alvarez.numbers.utils.Constants;

/**
 * Created by Daniel Alvarez on 4/8/16.
 * Copyright © 2016 Alvarez.tech. All rights reserved.
 */
public class RetrofitClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {

            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.YOUTUBE_DATA_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
