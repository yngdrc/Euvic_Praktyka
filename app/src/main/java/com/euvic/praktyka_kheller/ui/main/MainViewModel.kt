package com.euvic.praktyka_kheller.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.euvic.praktyka_kheller.db.HeroesDao
import com.euvic.praktyka_kheller.db.HeroesDatabase
import com.euvic.praktyka_kheller.db.model.HeroDataClass
import com.euvic.praktyka_kheller.db.model.HeroDetails
import com.euvic.praktyka_kheller.db.repo.HeroesRepo
import com.euvic.praktyka_kheller.db.repo.MainRepo
import com.euvic.praktyka_kheller.ui.main.state.MainStateEvent
import com.euvic.praktyka_kheller.ui.main.state.MainViewState
import com.euvic.praktyka_kheller.util.AbsentLiveData
import com.euvic.praktyka_kheller.util.DataState
import com.google.gson.JsonObject
import io.reactivex.rxjava3.core.Flowable

/*
Handling events
 */

class MainViewModel(application: Application): AndroidViewModel(application) {
    private val _stateEvent: MutableLiveData<MainStateEvent> = MutableLiveData()
    private val _viewState: MutableLiveData<MainViewState> = MutableLiveData()

    private val heroesDatabase: HeroesDao = HeroesDatabase.getDatabase(application).heroesDao()
    private val heroesRepo: HeroesRepo = HeroesRepo(heroesDatabase)

    val viewState: LiveData<MainViewState>
        get() = _viewState

    // Switchmap is listening for _stateEvent object, if it changes it will detect that change and execute given code
    val dataState: LiveData<DataState<MainViewState>> = Transformations
        .switchMap(_stateEvent) { stateEvent ->
            stateEvent?.let {
                handleStateEvent(stateEvent)
            }
        }

    private fun handleStateEvent(stateEvent: MainStateEvent): LiveData<DataState<MainViewState>> {
        println("DEBUG: New StateEvent detected: $stateEvent")
        return when(stateEvent) {
            is MainStateEvent.GetHeroesEvent -> {
                MainRepo.getHeroes(_stateEvent)
            }
            is MainStateEvent.GetHeroesFromDatabaseEvent -> {
                Log.d("MainViewModel", "get heroes from db")
                heroesRepo.getAllHeroes()
            }
            is MainStateEvent.GetDetailsEvent -> {
                MainRepo.setDetails(stateEvent.heroID)
            }
            is MainStateEvent.None -> {
                AbsentLiveData.create()
            }
        }
    }

    fun setHeroesListData(heroes: List<HeroDataClass>) {
//        if (heroesDatabase.getHeroes().isEmpty()) {
//            heroes.forEach{heroesDatabase.insert(it)}
//        } else {
//            heroes.forEach{heroesDatabase.update(it)}
//        }
        Log.d("MainViewModel", heroesDatabase.getHeroes().toString())
        val update = getCurrentViewStateOrNew()
        update.heroes = heroes
        _viewState.value = update
    }

    fun setDetails(details: HeroDataClass? = null){
        val update = getCurrentViewStateOrNew()
        update.details = details
        _viewState.value = update
    }

    private fun getCurrentViewStateOrNew(): MainViewState {
        return viewState.value ?: MainViewState()
    }

    fun setStateEvent(event: MainStateEvent) {
        _stateEvent.value = event
    }
}