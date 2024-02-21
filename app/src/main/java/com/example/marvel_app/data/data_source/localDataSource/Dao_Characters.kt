package com.example.marvel_app.data.data_source.localDataSource

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RoomWarnings
import com.example.marvel_app.domain.entities.CharactersCache


@Dao
interface Dao_Characters {

    @Query("SELECT * FROM characters_cache")
    suspend fun getAllTask(): List<CharactersCache>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: CharactersCache)

    @Delete
    suspend fun deleteTask(task: CharactersCache)

}