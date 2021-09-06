package com.euvic.praktyka_kheller.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.euvic.praktyka_kheller.db.model.HeroDataClass
import com.euvic.praktyka_kheller.db.repo.HeroesDatasource
import com.euvic.praktyka_kheller.ui.main.state.MainStateEvent
import com.euvic.praktyka_kheller.ui.main.state.MainViewState
import com.euvic.praktyka_kheller.util.DataState

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject


/*
Handling events
 */

class MainViewModel(application: Application): AndroidViewModel(application) {
    private var _stateEvent: BehaviorSubject<MainStateEvent> = BehaviorSubject.create()
    private var _viewState: BehaviorSubject<MainViewState> = BehaviorSubject.create()

    private val mainRepo = HeroesDatasource(application)

    val viewState: BehaviorSubject<MainViewState>
        get() = _viewState

    var compositeDisposable: CompositeDisposable = CompositeDisposable()
        get() = field

    val dataState: Observable<DataState<MainViewState>> = _stateEvent.subscribeOn(Schedulers.io()).switchMap {
        handleStateEvent(it)
    }


    private fun handleStateEvent(stateEvent: MainStateEvent): Observable<DataState<MainViewState>> {
        println("DEBUG: New StateEvent detected: $stateEvent")
        return when(stateEvent) {
            is MainStateEvent.GetHeroesEvent -> {
                mainRepo.getHeroes()
                // set state event GetHeroesFromDatabase
            }
            is MainStateEvent.GetDetailsEvent -> {
                //MainRepo.setDetails(stateEvent.heroID)
                mainRepo.getDetails(stateEvent.heroID)
            }
            is MainStateEvent.None -> {
                //MainRepo.setDetails(stateEvent.heroID)
                mainRepo.getHeroes()
            }
        }
    }

    fun setHeroesListData(heroes: List<HeroDataClass>) {
        val update = getCurrentViewStateOrNew()
        update.heroes = heroes
        _viewState.onNext(update)
        //_viewState.value = update
    }

    fun setDetails(details: HeroDataClass? = null){
        val update = getCurrentViewStateOrNew()
        update.details = details
        _viewState.onNext(update)
        //_viewState.value = update
    }

    private fun getCurrentViewStateOrNew(): MainViewState {
        return viewState.value ?: MainViewState()
    }

    fun setStateEvent(event: MainStateEvent) {
        _stateEvent.onNext(event)
        Log.d("_stateEvent", _stateEvent.value.toString())
        //_stateEvent.value = event
    }

    // onPause dispose()
    // onResume resubskrypcja

    override fun onCleared() {
        // clear disposables here
        compositeDisposable.dispose();
        super.onCleared()
    }
}