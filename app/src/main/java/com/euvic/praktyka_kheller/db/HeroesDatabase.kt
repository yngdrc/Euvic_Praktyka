package com.euvic.praktyka_kheller.db

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import com.euvic.praktyka_kheller.db.model.HeroDetails
import androidx.room.Room


@Database(entities = arrayOf(HeroDetails::class), version = 1)
abstract class HeroesDatabase: RoomDatabase() {
    abstract fun heroesDao(): HeroesDao

    companion object {
        @Volatile
        private var INSTANCE: HeroesDatabase? = null
        private val DB_NAME = "Heroes.db"

        fun getDatabase(context: Context): HeroesDatabase? {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HeroesDatabase::class.java,
                    DB_NAME
                )
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}