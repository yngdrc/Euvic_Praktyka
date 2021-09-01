package com.euvic.praktyka_kheller.db.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.euvic.praktyka_kheller.api.RetrofitBuilderOpenDota
import com.euvic.praktyka_kheller.db.HeroesDao
import com.euvic.praktyka_kheller.db.model.HeroDataClass
import com.euvic.praktyka_kheller.db.model.HeroDetails
import com.euvic.praktyka_kheller.ui.main.state.MainStateEvent
import com.euvic.praktyka_kheller.ui.main.state.MainViewState
import com.euvic.praktyka_kheller.util.*
import com.google.gson.Gson
import com.google.gson.JsonObject
import io.reactivex.rxjava3.core.Flowable

open class HeroesRepo(private val heroesDao: HeroesDao) {
    private val result = MediatorLiveData<DataState<MainViewState>>()
    fun insertHero(heroDetails: HeroDataClass) =
        heroesDao.insert(heroDetails)

    fun updateHeroes(heroDetails: HeroDataClass) =
        heroesDao.update(heroDetails)

    fun deleteHero(heroDetails: HeroDataClass) =
        heroesDao.delete(heroDetails)

    fun getAllHeroes(): LiveData<DataState<MainViewState>> {
        result.value = DataState.data(
            null,
            MainViewState(
                heroes = heroesDao.getHeroes(),
                details = null
            )
        )
        return result
    }
}

object MainRepo {
    val heroesList: ArrayList<HeroDataClass> = ArrayList()

    fun getHeroes(stateEvent: MutableLiveData<MainStateEvent>): LiveData<DataState<MainViewState>> {
        return object : NetworkBoundResource<JsonObject, MainViewState>() {
            override fun handleApiSuccessResponse(response: ApiSuccessResponse<JsonObject>) {
                Log.d("Heroes", response.body.toString())
                // result przeniesc do nbr
                val keySet = response.body.keySet()
                for(key in keySet) {
                    heroesList.add(Gson().fromJson(response.body[key], HeroDataClass::class.java))
                }
                result.value = DataState.data(
                    null,
                    MainViewState(
                        heroes = heroesList,
                        details = null
                    )
                )
            }

            override fun handleApiError(apiError: String) {
                Log.d("Repo", "error")
                val state: MainStateEvent = MainStateEvent.GetHeroesFromDatabaseEvent
                stateEvent.value = state
            }

            override fun createCall(): LiveData<GenericApiResponse<JsonObject>> {
                return RetrofitBuilderOpenDota.apiService.getAllHeroes()
            }

        }.asLiveData()
    }

    fun setDetails(heroID: Int): LiveData<DataState<MainViewState>> {
        return object : NetworkBoundResource<JsonObject, MainViewState>() {
            override fun handleApiSuccessResponse(response: ApiSuccessResponse<JsonObject>) {
                result.value = DataState.data(
                    null,
                    MainViewState(
                        heroes = null,
                        details = heroesList[heroID]
                    )
                )
            }

            override fun handleApiError(apiError: String) {
                Log.d("Repo", "error")
            }

            override fun createCall(): LiveData<GenericApiResponse<JsonObject>> {
                return RetrofitBuilderOpenDota.apiService.getAllHeroes()
            }

        }.asLiveData()
    }
}