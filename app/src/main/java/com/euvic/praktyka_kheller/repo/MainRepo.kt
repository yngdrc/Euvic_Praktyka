package com.euvic.praktyka_kheller.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.euvic.praktyka_kheller.api.RetrofitBuilder
import com.euvic.praktyka_kheller.model.HeroDetails
import com.euvic.praktyka_kheller.ui.main.state.MainViewState
import com.euvic.praktyka_kheller.util.*

object MainRepo {
    fun getHeroes(): LiveData<DataState<MainViewState>> {
        return object: NetworkBoundResource<List<HeroDetails>, MainViewState>() {
            override fun handleApiSuccessResponse(response: ApiSuccessResponse<List<HeroDetails>>) {
                result.value = DataState.data(
                    data = MainViewState(
                        heroes = response.body
                    )
                )
            }

            override fun createCall(): LiveData<GenericApiResponse<List<HeroDetails>>> {
                return RetrofitBuilder.apiService.getAllHeroes()
            }

        }.asLiveData()
    }
}