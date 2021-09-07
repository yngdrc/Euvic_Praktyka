package com.euvic.praktyka_kheller.db

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.euvic.praktyka_kheller.db.model.HeroDataClass

@Dao
interface HeroesDao {
    @Insert
    fun insert(hero: HeroDataClass)

    @Update
    fun update(hero: HeroDataClass)

    @Delete
    fun delete(hero: HeroDataClass)

    // potrzebne osobne query ktore sprawdzi czy jest jakikolwiek element (type Boolean)
//    @Query("")
//    fun isEmpty()

    @Query("SELECT * FROM heroes")
    fun getHeroes(): List<HeroDataClass>

    @RawQuery
    fun getHeroesViaQuery(query: SupportSQLiteQuery): HeroDataClass
}