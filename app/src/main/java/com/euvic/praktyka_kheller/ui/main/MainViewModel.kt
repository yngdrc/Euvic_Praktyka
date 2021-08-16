package com.euvic.praktyka_kheller.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.euvic.praktyka_kheller.db.model.HeroDetails
import com.euvic.praktyka_kheller.db.repo.MainRepo
import com.euvic.praktyka_kheller.ui.main.state.MainStateEvent
import com.euvic.praktyka_kheller.ui.main.state.MainViewState
import com.euvic.praktyka_kheller.util.AbsentLiveData
import com.euvic.praktyka_kheller.util.DataState

/*
Handling events
 */

class MainViewModel: ViewModel() {
    private val _stateEvent: MutableLiveData<MainStateEvent> = MutableLiveData()
    private val _viewState: MutableLiveData<MainViewState> = MutableLiveData()

    val viewState: LiveData<MainViewState>
    get() = _viewState

    // Switchmap is listening for _stateEvent object, if it changes it will detect that change and execute given code
    val dataState: LiveData<DataState<MainViewState>> = Transformations
        .switchMap(_stateEvent) { stateEvent ->
            stateEvent?.let {
                handleStateEvent(stateEvent)
            }
        }

    fun handleStateEvent(stateEvent: MainStateEvent): LiveData<DataState<MainViewState>> {
        println("DEBUG: New StateEvent detected: ${stateEvent}")
        when(stateEvent) {
            is MainStateEvent.GetHeroesEvent -> {
                return MainRepo.getHeroes()
            }
            is MainStateEvent.GetDetailsEvent -> {
                return MainRepo.setDetails(stateEvent.heroID)
            }
            is MainStateEvent.None -> {
                return AbsentLiveData.create()
            }
        }
    }

    fun setHeroesListData(heroes: List<HeroDetails>) {
        val update = getCurrentViewStateOrNew()
        update.heroes = heroes
        _viewState.value = update
    }

    fun setDetails(details: HeroDetails? = null){
        val update = getCurrentViewStateOrNew()
        update.details = details
        _viewState.value = update
    }

    fun getCurrentViewStateOrNew(): MainViewState {
        val value = viewState.value?.let { it }?: MainViewState()
        return value
    }

    fun setStateEvent(event: MainStateEvent) {
        val state: MainStateEvent
        state = event
        _stateEvent.value = state
    }
}
