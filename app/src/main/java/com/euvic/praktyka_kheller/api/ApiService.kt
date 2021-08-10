package com.euvic.praktyka_kheller.api

import androidx.lifecycle.LiveData
import com.euvic.praktyka_kheller.model.HeroDetails
import com.euvic.praktyka_kheller.util.GenericApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("heroes")
    fun getAllHeroes(): LiveData<GenericApiResponse<List<HeroDetails>>>

    @GET("hero/{heroId}")
    fun getHero(
        @Path("heroId") heroId: Int
    ): LiveData<GenericApiResponse<HeroDetails>>
}