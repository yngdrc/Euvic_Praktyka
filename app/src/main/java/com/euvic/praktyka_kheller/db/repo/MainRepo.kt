package com.euvic.praktyka_kheller.db.repo

import androidx.lifecycle.LiveData
import com.euvic.praktyka_kheller.api.RetrofitBuilderOpenDota
import com.euvic.praktyka_kheller.db.model.HeroDetails
import com.euvic.praktyka_kheller.ui.main.state.MainViewState
import com.euvic.praktyka_kheller.util.*

object MainRepo {
    fun getHeroes(): LiveData<DataState<MainViewState>> {
        return object: NetworkBoundResource<List<HeroDetails>, MainViewState>() {
            override fun handleApiSuccessResponse(response: ApiSuccessResponse<List<HeroDetails>>) {
                result.value = DataState.data(
                    null,
                    MainViewState(
                        heroes = response.body
                    )
                )
            }

            override fun createCall(): LiveData<GenericApiResponse<List<HeroDetails>>> {
                return RetrofitBuilderOpenDota.apiService.getAllHeroes()
            }

        }.asLiveData()
    }
}