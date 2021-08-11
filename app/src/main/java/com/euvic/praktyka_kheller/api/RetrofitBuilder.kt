package com.euvic.praktyka_kheller.api

import com.euvic.praktyka_kheller.util.LiveDataCallAdapter
import com.euvic.praktyka_kheller.util.LiveDataCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    const val OPENDOTA_HEROES_URL: String = "https://api.opendota.com/api/"
    const val STEAM_DOTA_IMAGES_URL: String = "https://cdn.dota2.com/apps/dota2/images/heroes/"

    val retrofitBuilder: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl(OPENDOTA_HEROES_URL)
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
    }

//    val retrofitBuilderHeroImage: Retrofit.Builder by lazy {
//        Retrofit.Builder()
//            .baseUrl(STEAM_DOTA_IMAGES_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .addCallAdapterFactory(LiveDataCallAdapterFactory())
//    }

    val apiService: ApiService by lazy {
        retrofitBuilder
            .build()
            .create(ApiService::class.java)
    }

//    val apiService2: ApiService by lazy {
//        retrofitBuilderHeroImage
//            .build()
//            .create(ApiService::class.java)
//    }
}