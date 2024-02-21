package com.example.marvel_app.data.data_source.localDataSource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.marvel_app.domain.entities.CharactersCache


@Database(entities = [CharactersCache::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun CharactersDao(): Dao_Characters

}

