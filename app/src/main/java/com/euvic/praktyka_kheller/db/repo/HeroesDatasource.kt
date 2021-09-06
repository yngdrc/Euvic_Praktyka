package com.euvic.praktyka_kheller.db.repo

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.euvic.praktyka_kheller.api.RetrofitBuilderOpenDota
import com.euvic.praktyka_kheller.db.HeroesDao
import com.euvic.praktyka_kheller.db.HeroesDatabase
import com.euvic.praktyka_kheller.db.model.HeroDataClass
import com.euvic.praktyka_kheller.ui.main.state.MainStateEvent
import com.euvic.praktyka_kheller.ui.main.state.MainViewState
import com.euvic.praktyka_kheller.util.*
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

// on error przechodzimy do database, dorzucenie dispose

class HeroesDatasource(application: Application) {
    private val heroesDatabase: HeroesDatabase = HeroesDatabase.getDatabase(application)

    fun getHeroes(): Observable<DataState<MainViewState>> {
        return RetrofitBuilderOpenDota.apiService.getAllHeroes().subscribeOn(Schedulers.io())
            .map {
                val keySet = it.keySet()
                val heroesList: ArrayList<HeroDataClass> = ArrayList<HeroDataClass>(keySet.size)
                keySet.forEach { key ->
                    heroesList.add(Gson().fromJson(it[key], HeroDataClass::class.java))
                }
                DataState.data(
                    null,
                    MainViewState(
                        heroes = heroesList,
                        details = null
                    )
                )
            }.onErrorResumeNext { throwable: Throwable ->
                heroesDatabase.heroesDao().getHeroes()
                return@onErrorResumeNext ObservableSource {
                    Log.d("Error", throwable.toString())
                }

            }
    }

    fun getDetails(index: Int): Observable<DataState<MainViewState>> {
        return RetrofitBuilderOpenDota.apiService.getAllHeroes().subscribeOn(Schedulers.io())
            .map {
                val keySet = it.keySet()
                val heroesList: ArrayList<HeroDataClass> = ArrayList<HeroDataClass>(keySet.size)
                keySet.forEach { key ->
                    heroesList.add(Gson().fromJson(it[key], HeroDataClass::class.java))
                }
                DataState.data(
                    null,
                    MainViewState(
                        heroes = null,
                        details = heroesList[index]
                    )
                )
            }.onErrorResumeNext { throwable: Throwable ->
                heroesDatabase.heroesDao().getHeroes()
                return@onErrorResumeNext ObservableSource {
                    Log.d("Error", throwable.toString())
                }

            }
    }
}