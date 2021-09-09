package com.euvic.praktyka_kheller.api

import com.google.gson.JsonObject
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface ApiServiceOpenDota {
    @GET("heroes")
    fun getAllHeroes(): Observable<JsonObject>
}