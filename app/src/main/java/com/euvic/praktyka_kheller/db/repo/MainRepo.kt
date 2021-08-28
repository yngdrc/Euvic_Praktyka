package com.euvic.praktyka_kheller.db.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.euvic.praktyka_kheller.api.RetrofitBuilderOpenDota
import com.euvic.praktyka_kheller.db.HeroesDao
import com.euvic.praktyka_kheller.db.HeroesDatabase
import com.euvic.praktyka_kheller.db.model.HeroDetails
import com.euvic.praktyka_kheller.ui.main.MainActivity
import com.euvic.praktyka_kheller.ui.main.state.MainStateEvent
import com.euvic.praktyka_kheller.ui.main.state.MainViewState
import com.euvic.praktyka_kheller.util.*

open class HeroesRepo(private val heroesDao: HeroesDao) {
    protected val result = MediatorLiveData<DataState<MainViewState>>()
    suspend fun insertHero(heroDetails: HeroDetails) =
        heroesDao.insert(heroDetails)

    suspend fun updateHeroes(heroDetails: HeroDetails) =
        heroesDao.update(heroDetails)

    suspend fun deleteHero(heroDetails: HeroDetails) =
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
    fun getHeroes(stateEvent: MutableLiveData<MainStateEvent>): LiveData<DataState<MainViewState>> {
        return object: NetworkBoundResource<List<HeroDetails>, MainViewState>() {
            override fun handleApiSuccessResponse(response: ApiSuccessResponse<List<HeroDetails>>) {
                result.value = DataState.data(
                    null,
                    MainViewState(
                        heroes = response.body,
                        details = null
                    )
                )
            }

            override fun handleApiError(response: String) {
                Log.d("Repo", "error")
                val state: MainStateEvent = MainStateEvent.GetHeroesFromDatabaseEvent()
                stateEvent.value = state
            }

            override fun createCall(): LiveData<GenericApiResponse<List<HeroDetails>>> {
                return RetrofitBuilderOpenDota.apiService.getAllHeroes()
            }

        }.asLiveData()
    }

    fun setDetails(heroID: Int): LiveData<DataState<MainViewState>> {
        return object: NetworkBoundResource<List<HeroDetails>, MainViewState>() {
            override fun handleApiSuccessResponse(response: ApiSuccessResponse<List<HeroDetails>>) {
                result.value = DataState.data(
                    null,
                    MainViewState(
                        heroes = null,
                        details = response.body[heroID]
                    )
                )
            }

            override fun handleApiError(apiError: String) {
                Log.d("Repo", "error")
            }

            override fun createCall(): LiveData<GenericApiResponse<List<HeroDetails>>> {
                return RetrofitBuilderOpenDota.apiService.getAllHeroes()
            }

        }.asLiveData()
    }
}