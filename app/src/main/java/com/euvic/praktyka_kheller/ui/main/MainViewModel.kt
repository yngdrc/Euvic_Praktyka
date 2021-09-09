package com.euvic.praktyka_kheller.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.euvic.praktyka_kheller.db.model.HeroDataClass
import com.euvic.praktyka_kheller.db.repo.HeroesDatasource
import com.euvic.praktyka_kheller.ui.main.state.MainStateEvent
import com.euvic.praktyka_kheller.ui.main.state.MainViewState
import com.euvic.praktyka_kheller.util.DataState
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.AsyncSubject
import io.reactivex.rxjava3.subjects.BehaviorSubject

class MainViewModel(application: Application): AndroidViewModel(application) {
    private var _stateEvent: BehaviorSubject<MainStateEvent> = BehaviorSubject.create()
    private var _viewState: BehaviorSubject<MainViewState> = BehaviorSubject.create()
    val EMPTY: MainStateEvent = MainStateEvent.None

    private val heroesDatasource = HeroesDatasource(application)

    val viewState: BehaviorSubject<MainViewState>
        get() = _viewState

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

//    val dataState: Observable<DataState<MainViewState>> = _stateEvent.switchMap {
//        Log.d("State event", _stateEvent.toString())
//        Log.d("state", it.toString())
//        handleStateEvent(it).startWithItem(DataState.loading(true))
//    }

    val dataState: Observable<DataState<MainViewState>> = _stateEvent.switchMap {
        Log.d("state", it.toString())
        handleStateEvent(it).startWithItem(DataState.loading(true))
    }


        private fun handleStateEvent(stateEvent: MainStateEvent): Observable<DataState<MainViewState>> {
            //println("DEBUG: New StateEvent detected: $stateEvent")
            return when(stateEvent) {
                is MainStateEvent.GetHeroesEvent -> {
                    heroesDatasource.getHeroes()
                    // set state event GetHeroesFromDatabase
                }
                is MainStateEvent.GetDetailsEvent -> {
                    //heroesDatasource.setDetails(stateEvent.heroID)
                    heroesDatasource.getDetails(stateEvent.heroID)
                }
                is MainStateEvent.None -> {
                    //heroesDatasource.setDetails(stateEvent.heroID)
                    heroesDatasource.getEmpty()
                }
            }
        }

        fun setHeroesListData(heroes: List<HeroDataClass>? = null) {
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
            //_stateEvent.onNext(EMPTY)
            //_stateEvent.value = event
        }

        override fun onCleared() {
            // clear disposables here
            compositeDisposable.dispose()
            super.onCleared()
        }
}