package com.euvic.praktyka_kheller.db.repo

import com.euvic.praktyka_kheller.db.HeroesDao
import com.euvic.praktyka_kheller.db.model.HeroDataClass
import com.euvic.praktyka_kheller.ui.main.state.MainViewState
import com.euvic.praktyka_kheller.util.DataState
import io.reactivex.Observable

open class HeroesRepo(private val heroesDao: HeroesDao) {
    fun insertHero(heroDetails: HeroDataClass) =
        heroesDao.insert(heroDetails)

    fun updateHeroes(heroDetails: HeroDataClass) =
        heroesDao.update(heroDetails)

    fun deleteHero(heroDetails: HeroDataClass) =
        heroesDao.delete(heroDetails)

    fun getAllHeroes(): Observable<DataState<MainViewState>> {
        return Observable.just(
            DataState.data(
                null,
                MainViewState(
                    heroes = heroesDao.getHeroes(),
                    details = null
                )
            )
        )
    }
}