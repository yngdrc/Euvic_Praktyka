package com.euvic.praktyka_kheller.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.euvic.praktyka_kheller.api.RetrofitBuilder
import com.euvic.praktyka_kheller.model.HeroDetails
import com.euvic.praktyka_kheller.model.HeroImage
import com.euvic.praktyka_kheller.ui.main.state.MainViewState
import com.euvic.praktyka_kheller.util.*

object MainRepo {
    fun getHeroes(): LiveData<DataState<MainViewState>> {
        return object: NetworkBoundResource<List<HeroDetails>, MainViewState>() {
            override fun handleApiSuccessResponse(response: ApiSuccessResponse<List<HeroDetails>>) {
                result.value = DataState.data(
                    null,
                    MainViewState(
                        heroes = response.body,
                        // heroImage = null
                    )
                )
            }

            override fun createCall(): LiveData<GenericApiResponse<List<HeroDetails>>> {
                return RetrofitBuilder.apiService.getAllHeroes()
            }

        }.asLiveData()
    }

//    fun getHeroImage(name: String, resolution: String): LiveData<DataState<MainViewState>> {
//        return object: NetworkBoundResource<HeroImage, MainViewState>() {
//            override fun handleApiSuccessResponse(response: ApiSuccessResponse<HeroImage>) {
//                result.value = DataState.data(
//                    data = MainViewState(
//                        heroes = null,
//                        heroImage = response.body
//                    )
//                )
//            }
//            override fun createCall(): LiveData<GenericApiResponse<HeroImage>> {
//                return RetrofitBuilder.apiService.getHeroImage(name, resolution)
//            }
//        }.asLiveData()
//    }
}