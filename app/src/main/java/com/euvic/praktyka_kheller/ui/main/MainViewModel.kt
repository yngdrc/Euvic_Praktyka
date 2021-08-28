package com.euvic.praktyka_kheller.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import androidx.room.RoomDatabase
import com.euvic.praktyka_kheller.db.HeroesDao
import com.euvic.praktyka_kheller.db.HeroesDatabase
import com.euvic.praktyka_kheller.db.model.HeroDetails
import com.euvic.praktyka_kheller.db.repo.HeroesRepo
import com.euvic.praktyka_kheller.db.repo.MainRepo
import com.euvic.praktyka_kheller.ui.main.state.MainStateEvent
import com.euvic.praktyka_kheller.ui.main.state.MainViewState
import com.euvic.praktyka_kheller.util.AbsentLiveData
import com.euvic.praktyka_kheller.util.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/*
Handling events
 */

class MainViewModel(application: Application): AndroidViewModel(application) {
    private val _stateEvent: MutableLiveData<MainStateEvent> = MutableLiveData()
    private val _viewState: MutableLiveData<MainViewState> = MutableLiveData()

    private val heroesRepo: HeroesRepo
    private val heroesDatabase: HeroesDao = HeroesDatabase.getDatabase(application)?.heroesDao()!!

    init {
        heroesRepo = HeroesRepo(heroesDatabase)
        //readAll = heroesRepo.getAllHeroes()
    }

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
                return MainRepo.getHeroes(_stateEvent)
            }
            is MainStateEvent.GetHeroesFromDatabaseEvent -> {
                Log.d("MainViewModel", "get heroes from db")
                return heroesRepo.getAllHeroes()
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
        if (heroesDatabase.getHeroes().isEmpty()) {
            heroes.forEach{heroesDatabase.insert(it)}
        } else {
            heroes.forEach{heroesDatabase.update(it)}
        }
        Log.d("MainViewModel", heroesDatabase.getHeroes().toString())
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
        val state: MainStateEvent = event
        _stateEvent.value = state
    }
}