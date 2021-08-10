package com.euvic.praktyka_kheller.api

import com.euvic.praktyka_kheller.model.HeroDetails
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("heroes")
    fun getAllHeroes(): List<HeroDetails>

    @GET("hero/{heroId}")
    fun getHero(
        @Path("heroId") heroId: Int
    ): HeroDetails
}