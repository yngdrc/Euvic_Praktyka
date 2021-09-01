package com.euvic.praktyka_kheller.db

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.Room
import com.euvic.praktyka_kheller.db.model.HeroDataClass
import com.euvic.praktyka_kheller.db.model.HeroDetails


@Database(entities = [HeroDataClass::class], version = 1)
abstract class HeroesDatabase: RoomDatabase() {
    abstract fun heroesDao(): HeroesDao

    companion object {
        @Volatile
        private var INSTANCE: HeroesDatabase? = null
        private const val DB_NAME = "Heroes.db"

        fun getDatabase(context: Context): HeroesDatabase {
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