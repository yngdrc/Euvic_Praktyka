package com.euvic.praktyka_kheller.api

import androidx.lifecycle.LiveData
import com.euvic.praktyka_kheller.db.model.HeroDetails
import com.euvic.praktyka_kheller.util.GenericApiResponse
import com.google.gson.JsonObject
import io.reactivex.rxjava3.core.Flowable
import retrofit2.http.GET

interface ApiServiceOpenDota {
    @GET("heroes")
    fun getAllHeroes(): LiveData<GenericApiResponse<JsonObject>>
}