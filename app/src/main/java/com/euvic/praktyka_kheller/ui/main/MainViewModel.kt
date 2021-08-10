package com.euvic.praktyka_kheller.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.euvic.praktyka_kheller.model.HeroDetails
import com.euvic.praktyka_kheller.ui.main.state.MainStateEvent
import com.euvic.praktyka_kheller.ui.main.state.MainViewState
import com.euvic.praktyka_kheller.util.AbsentLiveData

class MainViewModel: ViewModel() {
    private val _stateEvent: MutableLiveData<MainStateEvent> = MutableLiveData()
    private val _viewState: MutableLiveData<MainViewState> = MutableLiveData()

    val viewState: LiveData<MainViewState>
    get() = _viewState

    val dataState: LiveData<MainViewState> = Transformations
        .switchMap(_stateEvent) { stateEvent ->
            stateEvent?.let {
                handleStateEvent(stateEvent)
            }
        }

    fun handleStateEvent(stateEvent: MainStateEvent): LiveData<MainViewState> {
        println("DEBUG: New StateEvent detected: $stateEvent")
        when(stateEvent) {
            is MainStateEvent.GetHeroesEvent -> {
                return object: LiveData<MainViewState>() {
                    override fun onActive() {
                        super.onActive()
                        val heroesList: ArrayList<HeroDetails> = ArrayList()
                        heroesList.add(HeroDetails(
                            id = 1,
                            name = "npc_dota_hero_antimage",
                            localized_name = "Anti-Mage",
                            primary_attr = "agi",
                            attack_type = "Melee",
                            roles = listOf("Carry","Escape","Nuker")
                        ))
                        value = MainViewState(heroes = heroesList)
                    }
                }
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
