package com.euvic.praktyka_kheller.db

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQueryBuilder
import com.euvic.praktyka_kheller.db.model.HeroDetails

@Dao
interface HeroesDao {
    @Insert
    fun insert(hero: HeroDetails)

    @Update
    fun update(hero: HeroDetails)

    @Delete
    fun delete(hero: HeroDetails)

    @Query("SELECT * FROM heroes")
    fun getHeroes(): List<HeroDetails>

    @RawQuery
    fun getHeroesViaQuery(query: SupportSQLiteQuery): HeroDetails
}