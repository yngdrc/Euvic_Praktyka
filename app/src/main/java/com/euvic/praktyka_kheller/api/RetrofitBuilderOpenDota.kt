package com.euvic.praktyka_kheller.api

import com.euvic.praktyka_kheller.util.Constants.Companion.OPENDOTA_HEROES_URL
import com.euvic.praktyka_kheller.util.Constants.Companion.OPENDOTA_HEROES_URL_2
import com.euvic.praktyka_kheller.util.LiveDataCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilderOpenDota {

    private val retrofitBuilder: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl(OPENDOTA_HEROES_URL_2)
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
    }

    val apiService: ApiServiceOpenDota by lazy {
        retrofitBuilder
            .build()
            .create(ApiServiceOpenDota::class.java)
    }
}