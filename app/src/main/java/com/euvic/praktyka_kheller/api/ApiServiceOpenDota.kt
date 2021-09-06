package com.euvic.praktyka_kheller.api

import com.google.gson.JsonObject
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.GET

interface ApiServiceOpenDota {
    @GET("heroes")
    fun getAllHeroes(): Observable<JsonObject>
}