package com.euvic.praktyka_kheller.db.repo

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.euvic.praktyka_kheller.api.RetrofitBuilderOpenDota
import com.euvic.praktyka_kheller.db.HeroesDatabase
import com.euvic.praktyka_kheller.db.model.HeroDataClass
import com.euvic.praktyka_kheller.ui.main.state.MainViewState
import com.euvic.praktyka_kheller.util.*
import com.google.gson.Gson
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableSource
import io.reactivex.rxjava3.core.SingleSource
import io.reactivex.rxjava3.schedulers.Schedulers

// on error przechodzimy do database, dorzucenie dispose

class HeroesDatasource(application: Application) {
    private val heroesDatabase: HeroesDatabase = HeroesDatabase.getDatabase(application)
    private val heroesRepo: HeroesRepo = HeroesRepo(heroesDatabase.heroesDao())
    lateinit var heroDetails: HeroDataClass

    fun getHeroes(): Observable<DataState<MainViewState>> {
        return RetrofitBuilderOpenDota.apiService.getAllHeroes().subscribeOn(Schedulers.io())
            .map {
                val keySet = it.keySet()
                val heroesList: ArrayList<HeroDataClass> = ArrayList(keySet.size)
                keySet.forEach { key ->
                    heroDetails = Gson().fromJson(it[key], HeroDataClass::class.java)
                    heroesList.add(heroDetails)
                    if (heroesDatabase.heroesDao().getHeroes().isEmpty()) {
                        Log.d("Empty", "Empty")
                        heroesRepo
                            .insertHero(heroDetails)
                    } else {
                        heroesRepo
                            .updateHero(heroDetails)
                    }
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
                val heroesList: ArrayList<HeroDataClass> = ArrayList(keySet.size)
                keySet.forEach { key ->
                    heroDetails = Gson().fromJson(it[key], HeroDataClass::class.java)
                    heroesList.add(heroDetails)
                    if (heroesDatabase.heroesDao().getHeroes().isEmpty()) {
                        Log.d("Empty", "Empty")
                        heroesRepo
                            .insertHero(heroDetails)
                    } else {
                        heroesRepo
                            .updateHero(heroDetails)
                    }
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

    fun getEmpty(): Observable<DataState<MainViewState>> {
        return Observable.empty()
    }
}