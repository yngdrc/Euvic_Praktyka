package com.euvic.praktyka_kheller.api

import com.euvic.praktyka_kheller.util.Constants.Companion.OPENDOTA_HEROES_URL_2
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilderOpenDota {

    private val retrofitBuilder: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl(OPENDOTA_HEROES_URL_2)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
    }

    val apiService: ApiServiceOpenDota by lazy {
        retrofitBuilder
            .build()
            .create(ApiServiceOpenDota::class.java)
    }
}