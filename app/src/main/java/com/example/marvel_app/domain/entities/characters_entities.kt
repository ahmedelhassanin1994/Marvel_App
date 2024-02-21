package com.example.marvel_app.domain.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters_cache")
data class CharactersCache(
    @PrimaryKey(autoGenerate = true)
    val taskId: Int? = 0,
    val key: String,
    val date: String
)

