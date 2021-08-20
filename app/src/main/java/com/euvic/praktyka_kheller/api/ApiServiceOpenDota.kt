package com.euvic.praktyka_kheller.api

import androidx.lifecycle.LiveData
import com.euvic.praktyka_kheller.db.model.HeroDetails
import com.euvic.praktyka_kheller.util.GenericApiResponse
import retrofit2.http.GET

interface ApiServiceOpenDota {
    @GET("heroes")
    fun getAllHeroes(): LiveData<GenericApiResponse<List<HeroDetails>>>
}